package com.go_exchange_easier.backend.chat.room.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import java.io.Serializable;
import java.util.UUID;

@Schema(requiredProperties = {"id", "name"})
public record RoomSummary(

        UUID id,
        String name,
        @Nullable String imageUrl

) implements Serializable { }
