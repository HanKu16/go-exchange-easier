package com.go_exchange_easier.backend.core.domain.user.notification;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

@Entity
@Table(name = "notification_settings", schema = "core")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class NotificationSettings implements Persistable<UUID> {

    @Id
    @Column(name = "user_id")
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(name = "is_mail_notification_enabled")
    private boolean isMailNotificationEnabled;

    @Transient
    private boolean isNew = true;

    @Override
    public boolean isNew() {
        return isNew;
    }

    @PostLoad
    @PostPersist
    void markNotNew() {
        isNew = false;
    }

}