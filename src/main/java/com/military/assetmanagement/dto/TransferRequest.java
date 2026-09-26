package com.military.assetmanagement.dto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;
public record TransferRequest(
        @NotNull Long fromBaseId,
        @NotNull Long toBaseId,
        @NotNull LocalDateTime transferDate,
        String referenceNumber,
        @NotEmpty List<@Valid TransferItemRequest> items) {}
