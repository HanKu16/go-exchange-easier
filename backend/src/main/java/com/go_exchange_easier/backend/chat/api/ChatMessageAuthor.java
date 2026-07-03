package com.go_exchange_easier.backend.chat.api;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.UUID;

@Schema(requiredProperties = {"id", "nick"})
public record ChatMessageAuthor(
        UUID id,
        String nick
) implements Serializable { }
