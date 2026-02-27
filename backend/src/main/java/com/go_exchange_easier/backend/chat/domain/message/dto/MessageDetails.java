package com.go_exchange_easier.backend.chat.domain.message.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Schema(requiredProperties = {"id", "createdAt", "textContent", "author"})
public record MessageDetails(

        UUID id,
        Instant createdAt,
        String textContent,
        AuthorDetails author

) implements Serializable { }
