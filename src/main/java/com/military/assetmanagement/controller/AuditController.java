package com.military.assetmanagement.controller;

import com.military.assetmanagement.entity.AuditLog;
import com.military.assetmanagement.repository.AuditLogRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/audit-logs")
public class AuditController {
    private final AuditLogRepository repository;
    public AuditController(AuditLogRepository repository) { this.repository=repository; }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<AuditLog> all() { return repository.findAll(); }
}
