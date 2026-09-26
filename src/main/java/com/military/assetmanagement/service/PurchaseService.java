package com.military.assetmanagement.service;

import com.military.assetmanagement.dto.PurchaseRequest;
import com.military.assetmanagement.entity.Purchase;
import com.military.assetmanagement.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final BaseRepository baseRepository;
    private final EquipmentRepository equipmentRepository;
    private final CurrentUserService currentUser;
    private final AuditService audit;

    public PurchaseService(PurchaseRepository purchaseRepository, BaseRepository baseRepository,
                           EquipmentRepository equipmentRepository, CurrentUserService currentUser,
                           AuditService audit) {
        this.purchaseRepository = purchaseRepository;
        this.baseRepository = baseRepository;
        this.equipmentRepository = equipmentRepository;
        this.currentUser = currentUser;
        this.audit = audit;
    }

    @Transactional
    public Purchase create(PurchaseRequest r) {
        currentUser.checkBaseAccess(r.baseId());
        Purchase p = new Purchase();
        p.setBase(baseRepository.findById(r.baseId()).orElseThrow());
        p.setEquipment(equipmentRepository.findById(r.equipmentId()).orElseThrow());
        p.setQuantity(r.quantity());
        p.setPurchaseDate(r.purchaseDate());
        p.setSupplier(r.supplier());
        p.setReferenceNumber(r.referenceNumber());
        p.setCreatedBy(currentUser.get());
        p = purchaseRepository.save(p);
        audit.log(currentUser.get(), "CREATE_PURCHASE", "PURCHASE", p.getId(),
                "Quantity=" + p.getQuantity());
        return p;
    }

    public List<Purchase> find(LocalDate from, LocalDate to, Long baseId) {
        if (baseId != null) currentUser.checkBaseAccess(baseId);
        if (from == null) from = LocalDate.of(2000,1,1);
        if (to == null) to = LocalDate.now();
        return baseId == null ? purchaseRepository.findByPurchaseDateBetween(from,to)
                : purchaseRepository.findByBaseIdAndPurchaseDateBetween(baseId,from,to);
    }
}
