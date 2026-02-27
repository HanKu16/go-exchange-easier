package com.go_exchange_easier.backend.chat.domain.room.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import java.io.Serializable;
import java.util.UUID;

@Schema(requiredProperties = {"id", "name", "targetUserId"})
public record RoomSummary(

        UUID id,
        String name,
        Integer targetUserId,
        @Nullable String imageUrl

) implements Serializable { }
