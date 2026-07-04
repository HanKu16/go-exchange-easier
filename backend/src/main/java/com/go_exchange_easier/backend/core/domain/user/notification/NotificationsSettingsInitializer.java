package com.go_exchange_easier.backend.core.domain.user.notification;

import org.springframework.lang.Nullable;
import java.util.UUID;

public interface NotificationsSettingsInitializer {

    void initialize(
            UUID id,
            @Nullable String mail
    );

}
