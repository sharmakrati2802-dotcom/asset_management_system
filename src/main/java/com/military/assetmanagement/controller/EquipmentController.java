package com.military.assetmanagement.controller;

import com.military.assetmanagement.entity.Equipment;
import com.military.assetmanagement.repository.EquipmentRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/equipment")
public class EquipmentController {
    private final EquipmentRepository repository;
    public EquipmentController(EquipmentRepository repository) { this.repository=repository; }

    @GetMapping
    public List<Equipment> all() { return repository.findAll(); }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Equipment create(@RequestBody Equipment equipment) { return repository.save(equipment); }

    @GetMapping("/{id}")
    public Equipment get(@PathVariable Long id) { return repository.findById(id).orElseThrow(); }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Equipment update(@PathVariable Long id, @RequestBody Equipment input) {
        Equipment e=repository.findById(id).orElseThrow();
        e.setName(input.getName()); e.setEquipmentType(input.getEquipmentType());
        e.setDescription(input.getDescription()); e.setUnit(input.getUnit());
        return repository.save(e);
    }
}
