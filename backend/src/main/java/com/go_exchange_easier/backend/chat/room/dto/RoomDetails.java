package com.go_exchange_easier.backend.chat.room.dto;

import com.go_exchange_easier.backend.chat.message.dto.MessageDetails;
import com.go_exchange_easier.backend.common.dto.SimplePage;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import java.io.Serializable;
import java.util.UUID;

@Schema(requiredProperties = {"id", "name", "lastMessages"})
public record RoomDetails(

        UUID id,
        String name,
        @Nullable String imageUrl,
        SimplePage<MessageDetails> lastMessages

) implements Serializable { }
