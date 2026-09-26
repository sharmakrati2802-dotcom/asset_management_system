package com.military.assetmanagement.service;

import com.military.assetmanagement.entity.*;
import com.military.assetmanagement.repository.*;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class AuditService {
    private final AuditLogRepository repository;
    public AuditService(AuditLogRepository repository) { this.repository = repository; }

    public void log(User user, String action, String entityType, Long entityId, String details) {
        repository.save(new AuditLog(null, user, action, entityType, entityId, details, LocalDateTime.now()));
    }
}
