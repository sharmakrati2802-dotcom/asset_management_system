package com.military.assetmanagement.controller;

import com.military.assetmanagement.dto.DashboardResponse;
import com.military.assetmanagement.service.DashboardService;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    private final DashboardService service;
    public DashboardController(DashboardService service) { this.service=service; }

    @GetMapping
    public DashboardResponse dashboard(
            @RequestParam Long baseId,
            @RequestParam(required=false) LocalDate fromDate,
            @RequestParam(required=false) LocalDate toDate) {
        return service.dashboard(baseId,fromDate,toDate);
    }
}
