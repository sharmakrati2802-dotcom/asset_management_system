package com.military.assetmanagement.controller;

import com.military.assetmanagement.dto.TransferRequest;
import com.military.assetmanagement.entity.Transfer;
import com.military.assetmanagement.service.TransferService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {
    private final TransferService service;
    public TransferController(TransferService service) { this.service=service; }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','LOGISTICS_OFFICER')")
    public Transfer create(@Valid @RequestBody TransferRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<Transfer> history() { return service.history(); }
}
