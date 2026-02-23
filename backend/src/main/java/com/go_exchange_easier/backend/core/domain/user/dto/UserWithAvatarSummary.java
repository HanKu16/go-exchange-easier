package com.go_exchange_easier.backend.core.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import java.io.Serializable;

@Schema(requiredProperties = {"id", "nick"})
public record UserWithAvatarSummary(

        Integer id,
        String nick,
        @Nullable String avatarUrl

) implements Serializable { }
