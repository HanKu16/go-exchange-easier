package com.go_exchange_easier.backend.core.domain.auth.dto;

import java.io.Serializable;

public record TokenBundle(

        String accessToken,
        String refreshToken

) implements Serializable { }
