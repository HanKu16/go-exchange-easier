package com.go_exchange_easier.backend.chat.domain.room;

import java.time.OffsetDateTime;
import java.util.UUID;

public record RoomProjection (
        UUID id,
        UUID targetUserId,
        UUID lastMessageId,
        OffsetDateTime lastMessageCreatedAt,
        String lastMessageTextContent,
        UUID lastMessageAuthorId,
        OffsetDateTime lastReadAt
) { }
