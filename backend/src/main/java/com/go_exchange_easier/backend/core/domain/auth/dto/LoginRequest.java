package com.go_exchange_easier.backend.core.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record LoginRequest(

        @NotNull(message = "Login can not be blank.")
        @Schema(example = "DemoUser")
        String login,

        @NotNull(message = "Password can not be blank.")
        @Schema(example = "DemoUserPassword")
        String password

) { }
