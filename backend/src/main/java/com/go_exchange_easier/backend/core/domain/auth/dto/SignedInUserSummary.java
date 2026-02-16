package com.go_exchange_easier.backend.core.domain.auth.dto;

import java.io.Serializable;

public record SignedInUserSummary(

        Integer userId,
        String avatarUrl

) implements Serializable { }
