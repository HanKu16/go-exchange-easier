package com.go_exchange_easier.backend.chat.message.dto;

import java.time.OffsetDateTime;

public record MessageSummary(

        OffsetDateTime createdAt,
        String textContent,
        AuthorSummary author

) { }
