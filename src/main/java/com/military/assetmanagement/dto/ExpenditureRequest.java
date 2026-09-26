package com.military.assetmanagement.dto;
import jakarta.validation.constraints.*;
public record ExpenditureRequest(
        @NotNull Long baseId,
        @NotNull Long equipmentId,
        @NotNull @Positive Integer quantity,
        String reason) {}
