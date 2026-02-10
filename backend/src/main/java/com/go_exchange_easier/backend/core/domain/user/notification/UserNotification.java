package com.go_exchange_easier.backend.core.domain.user.notification;

import com.go_exchange_easier.backend.core.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_notifications", schema = "core")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserNotification {

    @Id
    @Column(name = "user_id")
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = "mail")
    private String mail;

    @Column(name = "is_mail_notification_enabled")
    private boolean isMailNotificationEnabled;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

}