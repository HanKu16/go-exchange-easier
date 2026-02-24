package com.go_exchange_easier.backend.chat.room.dto;

import com.go_exchange_easier.backend.chat.message.dto.MessageSummary;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import java.io.Serializable;
import java.util.UUID;

@Schema(requiredProperties = {"id", "name"})
public record RoomPreview(

        UUID id,
        String name,
        @Nullable String imageUrl,
        @Nullable MessageSummary lastMessage

) implements Serializable { }

