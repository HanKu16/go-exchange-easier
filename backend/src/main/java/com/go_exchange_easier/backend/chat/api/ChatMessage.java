package com.go_exchange_easier.backend.chat.api;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Schema(requiredProperties = {"id", "createdAt", "textContent", "author"})
public record ChatMessage(
        UUID id,
        Instant createdAt,
        String textContent,
        ChatMessageAuthor author
) implements Serializable { }
