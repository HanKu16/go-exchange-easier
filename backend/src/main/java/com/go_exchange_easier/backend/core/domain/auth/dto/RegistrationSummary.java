package com.go_exchange_easier.backend.core.domain.auth.dto;

import java.io.Serializable;
import java.time.OffsetDateTime;

public record RegistrationSummary(

        Integer userId,
        String login,
        String nick,
        OffsetDateTime createdAt

) implements Serializable { }
