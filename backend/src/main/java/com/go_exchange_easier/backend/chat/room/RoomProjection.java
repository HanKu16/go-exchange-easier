package com.go_exchange_easier.backend.chat.room;

import java.time.OffsetDateTime;
import java.util.UUID;

public record RoomProjection (

        UUID id,
        int targetUserId,
        UUID lastMessageId,
        OffsetDateTime lastMessageCreatedAt,
        String lastMessageTextContent,
        int lastMessageAuthorId

) { }
