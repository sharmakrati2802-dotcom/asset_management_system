package com.military.assetmanagement.service;

import com.military.assetmanagement.dto.AssignmentRequest;
import com.military.assetmanagement.entity.Assignment;
import com.military.assetmanagement.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AssignmentService {
    private final AssignmentRepository repository;
    private final BaseRepository bases;
    private final EquipmentRepository equipment;
    private final CurrentUserService currentUser;
    private final AuditService audit;

    public AssignmentService(AssignmentRepository repository, BaseRepository bases,
                             EquipmentRepository equipment, CurrentUserService currentUser,
                             AuditService audit) {
        this.repository=repository; this.bases=bases; this.equipment=equipment;
        this.currentUser=currentUser; this.audit=audit;
    }

    @Transactional
    public Assignment create(AssignmentRequest r) {
        currentUser.checkBaseAccess(r.baseId());
        Assignment a = new Assignment();
        a.setBase(bases.findById(r.baseId()).orElseThrow());
        a.setEquipment(equipment.findById(r.equipmentId()).orElseThrow());
        a.setPersonnelName(r.personnelName());
        a.setQuantity(r.quantity());
        a.setAssignedAt(LocalDateTime.now());
        a.setAssignedBy(currentUser.get());
        a = repository.save(a);
        audit.log(currentUser.get(),"CREATE_ASSIGNMENT","ASSIGNMENT",a.getId(),
                "Quantity="+a.getQuantity());
        return a;
    }

    public List<Assignment> list(Long baseId) {
        currentUser.checkBaseAccess(baseId);
        return repository.findByBaseId(baseId);
    }
}
