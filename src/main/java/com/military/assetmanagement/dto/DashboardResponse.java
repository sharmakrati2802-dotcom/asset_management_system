package com.military.assetmanagement.dto;
public record DashboardResponse(
        long openingBalance,
        long purchases,
        long transferIn,
        long transferOut,
        long netMovement,
        long assigned,
        long expended,
        long closingBalance) {}
