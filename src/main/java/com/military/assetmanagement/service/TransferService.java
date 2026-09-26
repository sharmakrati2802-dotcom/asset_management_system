package com.military.assetmanagement.service;

import com.military.assetmanagement.dto.*;
import com.military.assetmanagement.entity.*;
import com.military.assetmanagement.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransferService {

    private final TransferRepository transferRepository;
    private final TransferItemRepository transferItemRepository;
    private final BaseRepository baseRepository;
    private final EquipmentRepository equipmentRepository;
    private final CurrentUserService currentUser;
    private final AuditService audit;

    public TransferService(
            TransferRepository transferRepository,
            TransferItemRepository transferItemRepository,
            BaseRepository baseRepository,
            EquipmentRepository equipmentRepository,
            CurrentUserService currentUser,
            AuditService audit) {

        this.transferRepository = transferRepository;
        this.transferItemRepository = transferItemRepository;
        this.baseRepository = baseRepository;
        this.equipmentRepository = equipmentRepository;
        this.currentUser = currentUser;
        this.audit = audit;
    }

    @Transactional
    public Transfer create(TransferRequest r) {

        // 1. Validate base access
        currentUser.checkBaseAccess(r.fromBaseId());

        // 2. Validate source and destination
        if (r.fromBaseId().equals(r.toBaseId())) {
            throw new IllegalArgumentException(
                    "Source and destination must differ"
            );
        }

        // 3. Validate items
        if (r.items() == null || r.items().isEmpty()) {
            throw new IllegalArgumentException(
                    "Transfer must contain at least one item"
            );
        }

        // 4. Find source base
        Base fromBase = baseRepository.findById(r.fromBaseId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Source base not found: " + r.fromBaseId()
                        )
                );

        // 5. Find destination base
        Base toBase = baseRepository.findById(r.toBaseId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Destination base not found: " + r.toBaseId()
                        )
                );

        // 6. Create Transfer
        Transfer transfer = new Transfer();

        transfer.setFromBase(fromBase);
        transfer.setToBase(toBase);
        transfer.setTransferDate(r.transferDate());
        transfer.setReferenceNumber(r.referenceNumber());
        transfer.setStatus(TransferStatus.COMPLETED);
        transfer.setCreatedBy(currentUser.get());

        // 7. Create items
        for (TransferItemRequest itemRequest : r.items()) {

            if (itemRequest.quantity() <= 0) {
                throw new IllegalArgumentException(
                        "Transfer quantity must be greater than zero"
                );
            }

            Equipment equipment = equipmentRepository
                    .findById(itemRequest.equipmentId())
                    .orElseThrow(() ->
                            new IllegalArgumentException(
                                    "Equipment not found: "
                                            + itemRequest.equipmentId()
                            )
                    );

            TransferItem item = new TransferItem();

            item.setEquipment(equipment);
            item.setQuantity(itemRequest.quantity());

            // VERY IMPORTANT
            transfer.addItem(item);
        }

        // 8. Save parent + children using cascade
        Transfer savedTransfer =
                transferRepository.saveAndFlush(transfer);

        // 9. Audit
        audit.log(
                currentUser.get(),
                "CREATE_TRANSFER",
                "TRANSFER",
                savedTransfer.getId(),
                "From=" + r.fromBaseId()
                        + ", To=" + r.toBaseId()
        );

        return savedTransfer;
    }
    /**
     * Get transfer history.
     */

    //@Transactional(readOnly = true)
    /*public List<Transfer> history() {

        User user = currentUser.get();

        // ADMIN can see all transfers
        if (user.getRole() == Role.ADMIN) {
            return transferRepository.findAll();
        }

        // User without a base gets an empty list
        if (user.getBase() == null) {
            return List.of();
        }

        // Base users can see transfers
        // from or to their assigned base
        Long baseId = user.getBase().getId();

        return transferRepository
                .findByFromBaseIdOrToBaseId(baseId, baseId);
    }*/

    public List<Transfer> history() {

        User user = currentUser.get();

        // ADMIN can see all transfers
        if (user.getRole() == Role.ADMIN) {
            return transferRepository.findAllWithDetails();
        }

        // User without a base gets an empty list
        if (user.getBase() == null) {
            return List.of();
        }

        Long baseId = user.getBase().getId();

        return transferRepository.findHistoryByBaseId(baseId);
    }
}