package com.go_exchange_easier.backend.core.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import java.io.Serializable;
import java.util.UUID;

@Schema(requiredProperties = {"id", "nick"})
public record UserWithAvatarSummary(
        UUID id,
        String nick,

        @Nullable
        String avatarUrl

) implements Serializable { }
