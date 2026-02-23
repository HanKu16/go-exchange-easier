package com.go_exchange_easier.backend.chat.message.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.Instant;

@Schema(requiredProperties = {"createdAt", "textContent", "author"})
public record MessageSummary(

        Instant createdAt,
        String textContent,
        AuthorSummary author

) implements Serializable { }
