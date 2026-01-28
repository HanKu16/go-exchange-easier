package com.go_exchange_easier.backend.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request body for login a user for the first time")
public record LoginRequest(

        @NotNull(message = "Login can not be blank.")
        @Schema(example = "DuckyKentucky1")
        String login,

        @NotNull(message = "Password can not be blank.")
        @Schema(example = "Basketball23")
        String password

) { }
