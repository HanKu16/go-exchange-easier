package com.go_exchange_easier.backend.domain.auth.dto;

import com.go_exchange_easier.backend.domain.user.dto.SignedInUserSummary;

public record LoginSummary(

        SignedInUserSummary signedInUserSummary,
        TokenBundle tokenBundle

) { }
