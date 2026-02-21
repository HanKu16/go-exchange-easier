package com.go_exchange_easier.backend.chat.message.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public record MessageDetails(

        UUID id,
        Instant createdAt,
        String textContent,
        AuthorDetails author

) implements Serializable { }
