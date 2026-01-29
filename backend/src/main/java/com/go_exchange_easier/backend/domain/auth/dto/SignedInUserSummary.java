package com.go_exchange_easier.backend.domain.auth.dto;

public record SignedInUserSummary(

        Integer userId,
        String avatarUrl

) { }
