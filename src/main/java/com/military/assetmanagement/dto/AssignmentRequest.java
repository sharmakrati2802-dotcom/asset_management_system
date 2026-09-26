package com.military.assetmanagement.dto;
import jakarta.validation.constraints.*;
public record AssignmentRequest(
        @NotNull Long baseId,
        @NotNull Long equipmentId,
        @NotBlank String personnelName,
        @NotNull @Positive Integer quantity) {}
