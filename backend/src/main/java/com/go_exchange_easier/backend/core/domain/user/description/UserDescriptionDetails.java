package com.go_exchange_easier.backend.core.domain.user.description;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.OffsetDateTime;

@Schema(requiredProperties = {"userId", "description", "updatedAt"})
public record UserDescriptionDetails(

        Integer userId,
        String description,
        OffsetDateTime updatedAt

) implements Serializable { }
