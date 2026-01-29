package com.go_exchange_easier.backend.domain.auth.dto;

public record TokenBundle(

        String accessToken,
        String refreshToken

) { }
