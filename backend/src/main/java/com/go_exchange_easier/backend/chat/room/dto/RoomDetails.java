package com.go_exchange_easier.backend.chat.room.dto;

import com.go_exchange_easier.backend.chat.message.dto.MessageSummary;
import com.go_exchange_easier.backend.common.dto.SimplePage;
import java.util.UUID;

public record RoomDetails(

        UUID id,
        String name,
        String imageUrl,
        SimplePage<MessageSummary> lastMessages

) { }
