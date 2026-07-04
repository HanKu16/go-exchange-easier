package com.go_exchange_easier.backend.core.domain.user;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface UserInitializer {

    void initialize(
            UUID id,
            String nick,
            OffsetDateTime createdAt
    );

}
