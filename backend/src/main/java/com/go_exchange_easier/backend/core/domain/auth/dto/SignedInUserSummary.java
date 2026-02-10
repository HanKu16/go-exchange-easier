package com.go_exchange_easier.backend.core.domain.auth.dto;

public record SignedInUserSummary(

        Integer userId,
        String avatarUrl

) { }
