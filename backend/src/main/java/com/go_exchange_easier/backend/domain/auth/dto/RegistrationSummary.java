package com.go_exchange_easier.backend.domain.auth.dto;

import java.time.OffsetDateTime;

public record RegistrationSummary(

        Integer userId,
        String login,
        String nick,
        OffsetDateTime createdAt

) { }
