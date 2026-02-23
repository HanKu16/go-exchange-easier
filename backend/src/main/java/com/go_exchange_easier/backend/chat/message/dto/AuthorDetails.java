package com.go_exchange_easier.backend.chat.message.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import java.io.Serializable;

@Schema(requiredProperties = {"id", "nick"})
public record AuthorDetails(

        Integer id,
        String nick,
        @Nullable String avatarUrl

) implements Serializable { }
