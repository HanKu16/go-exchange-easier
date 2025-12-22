package com.go_exchange_easier.backend.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.OffsetDateTime;

@Schema(description = "Response body for a successful user registration")
public record UserRegistrationResponse(

        Integer userId,
        String login,
        String nick,
        OffsetDateTime createdAt

) { }
