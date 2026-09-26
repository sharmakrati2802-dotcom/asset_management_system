package com.military.assetmanagement.controller;

import com.military.assetmanagement.dto.PurchaseRequest;
import com.military.assetmanagement.entity.Purchase;
import com.military.assetmanagement.service.PurchaseService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {
    private final PurchaseService service;
    public PurchaseController(PurchaseService service) { this.service=service; }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','LOGISTICS_OFFICER')")
    public Purchase create(@Valid @RequestBody PurchaseRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<Purchase> list(@RequestParam(required=false) LocalDate from,
                               @RequestParam(required=false) LocalDate to,
                               @RequestParam(required=false) Long baseId) {
        return service.find(from,to,baseId);
    }
}
