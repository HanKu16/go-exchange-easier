package com.go_exchange_easier.backend.chat.domain.message.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import java.io.Serializable;
import java.util.UUID;

@Schema(requiredProperties = {"id", "nick"})
public record AuthorDetails(
        UUID id,
        String nick,

        @Nullable
        String avatarUrl

) implements Serializable {}
