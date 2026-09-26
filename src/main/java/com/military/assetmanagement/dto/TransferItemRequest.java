package com.military.assetmanagement.dto;
import jakarta.validation.constraints.*;
public record TransferItemRequest(@NotNull Long equipmentId, @NotNull @Positive Integer quantity) {}
