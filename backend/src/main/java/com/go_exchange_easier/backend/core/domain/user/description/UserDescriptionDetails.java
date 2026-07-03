package com.go_exchange_easier.backend.core.domain.user.description;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

@Schema(requiredProperties = {"userId", "description", "updatedAt"})
public record UserDescriptionDetails(
        UUID userId,
        String description,
        OffsetDateTime updatedAt
) implements Serializable { }
