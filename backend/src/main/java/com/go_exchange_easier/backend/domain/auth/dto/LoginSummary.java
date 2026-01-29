package com.go_exchange_easier.backend.domain.auth.dto;

public record LoginSummary(

        SignedInUserSummary signedInUserSummary,
        TokenBundle tokenBundle

) { }
