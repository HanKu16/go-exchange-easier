package com.go_exchange_easier.backend.core.domain.user.listener;

import com.go_exchange_easier.backend.core.domain.auth.event.UserRegisteredEvent;
import com.go_exchange_easier.backend.core.domain.user.UserInitializer;
import com.go_exchange_easier.backend.core.domain.user.description.UserDescriptionInitializer;
import com.go_exchange_easier.backend.core.domain.user.notification.NotificationsSettingsInitializer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRegisteredListener {

    private final NotificationsSettingsInitializer notificationsSettingsInitializer;
    private final UserDescriptionInitializer userDescriptionInitializer;
    private final UserInitializer userInitializer;

    @EventListener
    public void handleUserRegisteredEvent(UserRegisteredEvent event) {
        userInitializer.initialize(event.userId(), event.nick(), event.createdAt());
        notificationsSettingsInitializer.initialize(event.userId());
        userDescriptionInitializer.initialize(event.userId());
    }

}
