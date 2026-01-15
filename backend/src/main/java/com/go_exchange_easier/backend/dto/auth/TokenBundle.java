package com.go_exchange_easier.backend.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Container for tokens")
public record TokenBundle(

        String accessToken,
        String refreshToken

) { }
