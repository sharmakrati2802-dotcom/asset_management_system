package com.military.assetmanagement.dto;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
public record PurchaseRequest(
        @NotNull Long baseId,
        @NotNull Long equipmentId,
        @NotNull @Positive Integer quantity,
        @NotNull LocalDate purchaseDate,
        String supplier,
        String referenceNumber) {}
