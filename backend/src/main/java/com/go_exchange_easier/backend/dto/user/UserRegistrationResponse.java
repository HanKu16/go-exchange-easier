package com.go_exchange_easier.backend.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.OffsetDateTime;

@Schema(description = "Response body for a successful user registration")
public record UserRegistrationResponse(

        @Schema(example = "23")
        Integer userId,

        @Schema(example = "DuckyKentucky1")
        String login,

        @Schema(example = "Michael")
        String nick,

        @Schema(example = "2025-08-12T19:56:36.118Z")
        OffsetDateTime createdAt

) { }
