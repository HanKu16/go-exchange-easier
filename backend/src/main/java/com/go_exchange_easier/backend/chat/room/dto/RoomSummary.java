package com.go_exchange_easier.backend.chat.room.dto;

import com.go_exchange_easier.backend.chat.message.dto.MessageSummary;
import java.util.UUID;

public record RoomSummary(

        UUID id,
        MessageSummary lastMessage

) { }

