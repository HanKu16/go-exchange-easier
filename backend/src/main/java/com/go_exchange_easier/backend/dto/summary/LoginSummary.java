package com.go_exchange_easier.backend.dto.summary;

import com.go_exchange_easier.backend.dto.auth.TokenBundle;

public record LoginSummary(

        SignedInUserSummary signedInUserSummary,
        TokenBundle tokenBundle

) { }
