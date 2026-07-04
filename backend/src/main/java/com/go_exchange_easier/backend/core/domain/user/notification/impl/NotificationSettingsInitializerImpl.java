package com.go_exchange_easier.backend.core.domain.user.notification.impl;

import com.go_exchange_easier.backend.core.domain.user.notification.NotificationSettings;
import com.go_exchange_easier.backend.core.domain.user.notification.NotificationSettingsRepository;
import com.go_exchange_easier.backend.core.domain.user.notification.NotificationsSettingsInitializer;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationSettingsInitializerImpl implements NotificationsSettingsInitializer {

    private final NotificationSettingsRepository notificationSettingsRepository;

    @Override
    @Transactional
    public void initialize(
            UUID id
    ) {
        NotificationSettings notificationSettings = new NotificationSettings();
        notificationSettings.setId(id);
        notificationSettings.setMailNotificationEnabled(true);
        notificationSettingsRepository.save(notificationSettings);
    }

}
