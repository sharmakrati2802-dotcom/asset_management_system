package com.military.assetmanagement.controller;

import com.military.assetmanagement.dto.AssignmentRequest;
import com.military.assetmanagement.entity.Assignment;
import com.military.assetmanagement.service.AssignmentService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {
    private final AssignmentService service;
    public AssignmentController(AssignmentService service) { this.service=service; }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','BASE_COMMANDER')")
    public Assignment create(@Valid @RequestBody AssignmentRequest request) {
        return service.create(request);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','BASE_COMMANDER')")
    public List<Assignment> list(@RequestParam Long baseId) { return service.list(baseId); }
}
