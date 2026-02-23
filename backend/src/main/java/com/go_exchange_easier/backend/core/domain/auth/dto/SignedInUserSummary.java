package com.go_exchange_easier.backend.core.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import java.io.Serializable;

@Schema(requiredProperties = {"userId"})
public record SignedInUserSummary(

        Integer userId,
        @Nullable String avatarUrl

) implements Serializable { }
