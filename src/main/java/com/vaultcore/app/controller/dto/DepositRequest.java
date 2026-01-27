package com.vaultcore.app.controller.dto;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DepositRequest(
    @NotNull UUID accountId,
    @NotNull @Positive BigDecimal amount
) {}

