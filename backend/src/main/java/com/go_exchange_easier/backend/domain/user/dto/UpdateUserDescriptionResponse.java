package com.go_exchange_easier.backend.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;

@Schema(description = "Response body for successful user description update")
public record UpdateUserDescriptionResponse(

        Integer userId,
        String description,
        OffsetDateTime updatedAt

) { }
