package com.military.assetmanagement.service;

import com.military.assetmanagement.dto.ExpenditureRequest;
import com.military.assetmanagement.entity.Expenditure;
import com.military.assetmanagement.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExpenditureService {
    private final ExpenditureRepository repository;
    private final BaseRepository bases;
    private final EquipmentRepository equipment;
    private final CurrentUserService currentUser;
    private final AuditService audit;

    public ExpenditureService(ExpenditureRepository repository, BaseRepository bases,
                              EquipmentRepository equipment, CurrentUserService currentUser,
                              AuditService audit) {
        this.repository=repository; this.bases=bases; this.equipment=equipment;
        this.currentUser=currentUser; this.audit=audit;
    }

    @Transactional
    public Expenditure create(ExpenditureRequest r) {
        currentUser.checkBaseAccess(r.baseId());
        Expenditure e = new Expenditure();
        e.setBase(bases.findById(r.baseId()).orElseThrow());
        e.setEquipment(equipment.findById(r.equipmentId()).orElseThrow());
        e.setQuantity(r.quantity());
        e.setReason(r.reason());
        e.setExpendedAt(LocalDateTime.now());
        e.setRecordedBy(currentUser.get());
        e = repository.save(e);
        audit.log(currentUser.get(),"CREATE_EXPENDITURE","EXPENDITURE",e.getId(),
                "Quantity="+e.getQuantity());
        return e;
    }

    public List<Expenditure> list(Long baseId) {
        currentUser.checkBaseAccess(baseId);
        return repository.findByBaseId(baseId);
    }
}
