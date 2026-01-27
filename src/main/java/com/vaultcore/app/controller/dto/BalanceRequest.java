package com.vaultcore.app.controller.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record BalanceRequest(
    @NotNull UUID accountId
) {}

