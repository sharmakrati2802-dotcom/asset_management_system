package com.military.assetmanagement.controller;

import com.military.assetmanagement.entity.Base;
import com.military.assetmanagement.repository.BaseRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/bases")
public class BaseController {
    private final BaseRepository repository;
    public BaseController(BaseRepository repository) { this.repository=repository; }

    @GetMapping
    public List<Base> all() { return repository.findAll(); }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Base create(@RequestBody Base base) { return repository.save(base); }

    @GetMapping("/{id}")
    public Base get(@PathVariable Long id) { return repository.findById(id).orElseThrow(); }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Base update(@PathVariable Long id, @RequestBody Base input) {
        Base b = repository.findById(id).orElseThrow();
        b.setName(input.getName()); b.setLocation(input.getLocation());
        return repository.save(b);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) { repository.deleteById(id); }
}
