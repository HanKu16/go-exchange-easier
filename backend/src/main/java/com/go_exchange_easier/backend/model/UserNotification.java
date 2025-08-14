package com.go_exchange_easier.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_notifications")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserNotification {

    @Id
    @Column(name = "user_notification_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = "mail")
    private String mail;

    @Column(name = "is_mail_notification_enabled")
    private boolean isMailNotificationEnabled;

    @OneToOne(mappedBy = "notification")
    private User user;

}