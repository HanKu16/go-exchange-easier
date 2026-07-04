package com.go_exchange_easier.backend.core.domain.user.dto;

import jakarta.annotation.Nullable;
import java.time.OffsetDateTime;
import java.util.UUID;

public record BasicUser(
        UUID id,
        String nick,

        @Nullable
        String avatarKey,

        @Nullable
        OffsetDateTime deletedAt,

        Boolean isBlocked
) { }
