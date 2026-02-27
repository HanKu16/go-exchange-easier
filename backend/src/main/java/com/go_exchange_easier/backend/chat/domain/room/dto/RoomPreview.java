package com.go_exchange_easier.backend.chat.domain.room.dto;

import com.go_exchange_easier.backend.chat.domain.message.dto.MessageSummary;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import java.io.Serializable;
import java.util.UUID;

@Schema(requiredProperties = {"id", "name", "targetUserId", "hasAnyUnreadMessages"})
public record RoomPreview(

        UUID id,
        String name,
        Integer targetUserId,
        boolean hasAnyUnreadMessages,
        @Nullable String imageUrl,
        @Nullable MessageSummary lastMessage

) implements Serializable { }

