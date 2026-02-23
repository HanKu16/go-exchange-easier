package com.go_exchange_easier.backend.core.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Schema(requiredProperties = {"userId", "login", "nick", "createdAt"})
public record RegistrationSummary(

        Integer userId,
        String login,
        String nick,
        OffsetDateTime createdAt

) implements Serializable { }
