package com.go_exchange_easier.backend.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response body for a successful user login")
public record TokenBundle(

        Integer userId,
        String accessToken,
        String tokenType

) { }
