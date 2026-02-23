package com.go_exchange_easier.backend.chat.message.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import java.io.Serializable;

@Schema(requiredProperties = {"nick"})
public record AuthorSummary(

        String nick,
        @Nullable String avatarUrl

) implements Serializable { }
