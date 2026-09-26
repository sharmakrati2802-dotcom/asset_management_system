package com.military.assetmanagement.controller;

import com.military.assetmanagement.dto.ExpenditureRequest;
import com.military.assetmanagement.entity.Expenditure;
import com.military.assetmanagement.service.ExpenditureService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/expenditures")
public class ExpenditureController {
    private final ExpenditureService service;
    public ExpenditureController(ExpenditureService service) { this.service=service; }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','BASE_COMMANDER')")
    public Expenditure create(@Valid @RequestBody ExpenditureRequest request) {
        return service.create(request);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','BASE_COMMANDER')")
    public List<Expenditure> list(@RequestParam Long baseId) { return service.list(baseId); }
}
