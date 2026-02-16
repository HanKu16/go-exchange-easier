package com.go_exchange_easier.backend.chat.message.dto;

import java.io.Serializable;
import java.time.Instant;

public record MessageSummary(

        Instant createdAt,
        String textContent,
        AuthorSummary author

) implements Serializable { }
