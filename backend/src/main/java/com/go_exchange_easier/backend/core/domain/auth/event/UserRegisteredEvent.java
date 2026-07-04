package com.go_exchange_easier.backend.core.domain.auth.event;

import jakarta.annotation.Nullable;
import java.time.OffsetDateTime;
import java.util.UUID;

public record UserRegisteredEvent(
        UUID userId,
        String nick,

        @Nullable
        String mail,

        OffsetDateTime createdAt
) { }
