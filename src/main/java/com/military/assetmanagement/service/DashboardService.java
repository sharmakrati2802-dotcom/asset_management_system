package com.military.assetmanagement.service;

import com.military.assetmanagement.dto.DashboardResponse;
import com.military.assetmanagement.entity.*;
import com.military.assetmanagement.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class DashboardService {
    private final PurchaseRepository purchases;
    private final TransferRepository transfers;
    private final AssignmentRepository assignments;
    private final ExpenditureRepository expenditures;
    private final CurrentUserService currentUser;

    public DashboardService(PurchaseRepository purchases, TransferRepository transfers,
                            AssignmentRepository assignments, ExpenditureRepository expenditures,
                            CurrentUserService currentUser) {
        this.purchases=purchases; this.transfers=transfers; this.assignments=assignments;
        this.expenditures=expenditures; this.currentUser=currentUser;
    }

    /*
    public DashboardResponse dashboard(Long baseId, LocalDate from, LocalDate to) {
        currentUser.checkBaseAccess(baseId);
        if (from == null) from = LocalDate.of(2000,1,1);
        if (to == null) to = LocalDate.now();

        long purchase = purchases.findByBaseIdAndPurchaseDateBetween(baseId, from, to)
                .stream().mapToLong(Purchase::getQuantity).sum();

        LocalDate finalFrom = from;
        LocalDate finalTo = to;
        var ts = transfers.findByFromBaseIdOrToBaseId(baseId, baseId).stream()
                .filter(t -> t.getTransferDate().toLocalDate().compareTo(finalFrom) >= 0
                          && t.getTransferDate().toLocalDate().compareTo(finalTo) <= 0)
                .toList();

        long in = ts.stream().filter(t -> t.getToBase().getId().equals(baseId))
                .flatMap(t -> t.getItems().stream()).mapToLong(TransferItem::getQuantity).sum();

        long out = ts.stream().filter(t -> t.getFromBase().getId().equals(baseId))
                .flatMap(t -> t.getItems().stream()).mapToLong(TransferItem::getQuantity).sum();

        LocalDate finalFrom1 = from;
        LocalDate finalTo1 = to;
        long assigned = assignments.findByBaseId(baseId).stream()
                .filter(a -> !a.getAssignedAt().toLocalDate().isBefore(finalFrom1)
                          && !a.getAssignedAt().toLocalDate().isAfter(finalTo1))
                .mapToLong(Assignment::getQuantity).sum();

        LocalDate finalFrom2 = from;
        LocalDate finalTo2 = to;
        long expended = expenditures.findByBaseId(baseId).stream()
                .filter(e -> !e.getExpendedAt().toLocalDate().isBefore(finalFrom2)
                          && !e.getExpendedAt().toLocalDate().isAfter(finalTo2))
                .mapToLong(Expenditure::getQuantity).sum();

        long net = purchase + in - out;
        // Opening balance is intentionally exposed as 0 in this initial framework.
        // A production implementation should calculate it from all transactions before 'from'.
        long opening = 0;
        long closing = opening + net - expended;

        return new DashboardResponse(opening,purchase,in,out,net,assigned,expended,closing);
    }*/

    @Transactional(readOnly = true)
    public DashboardResponse dashboard(Long baseId, LocalDate from, LocalDate to) {

        currentUser.checkBaseAccess(baseId);

        if (from == null) {
            from = LocalDate.of(2000, 1, 1);
        }

        if (to == null) {
            to = LocalDate.now();
        }

        final LocalDate finalFrom = from;
        final LocalDate finalTo = to;

        long purchase = purchases
                .findByBaseIdAndPurchaseDateBetween(baseId, from, to)
                .stream()
                .mapToLong(Purchase::getQuantity)
                .sum();


        var transfersList = transfers
                .findByFromBaseIdOrToBaseId(baseId, baseId)
                .stream()
                .filter(t -> t.getTransferDate() != null)
                .filter(t -> {
                    LocalDate date = t.getTransferDate().toLocalDate();
                    return !date.isBefore(finalFrom) && !date.isAfter(finalTo);
                })
                .toList();

        long in = transfersList.stream()
                .filter(t -> t.getToBase() != null)
                .filter(t -> t.getToBase().getId() != null)
                .filter(t -> t.getToBase().getId().equals(baseId))
                .filter(t -> t.getItems() != null)
                .flatMap(t -> t.getItems().stream())
                .mapToLong(TransferItem::getQuantity)
                .sum();

        long out = transfersList.stream()
                .filter(t -> t.getFromBase() != null)
                .filter(t -> t.getFromBase().getId() != null)
                .filter(t -> t.getFromBase().getId().equals(baseId))
                .filter(t -> t.getItems() != null)
                .flatMap(t -> t.getItems().stream())
                .mapToLong(TransferItem::getQuantity)
                .sum();

        long assigned = assignments
                .findByBaseId(baseId)
                .stream()
                .filter(a -> a.getAssignedAt() != null)
                .filter(a -> {
                    LocalDate date = a.getAssignedAt().toLocalDate();
                    return !date.isBefore(finalFrom) && !date.isAfter(finalTo);
                })
                .mapToLong(Assignment::getQuantity)
                .sum();

        long expended = expenditures
                .findByBaseId(baseId)
                .stream()
                .filter(e -> e.getExpendedAt() != null)
                .filter(e -> {
                    LocalDate date = e.getExpendedAt().toLocalDate();
                    return !date.isBefore(finalFrom) && !date.isAfter(finalTo);
                })
                .mapToLong(Expenditure::getQuantity)
                .sum();

        long net = purchase + in - out;

        long opening = 0;

        long closing = opening + net - expended;

        return new DashboardResponse(
                opening,
                purchase,
                in,
                out,
                net,
                assigned,
                expended,
                closing
        );
    }
}
