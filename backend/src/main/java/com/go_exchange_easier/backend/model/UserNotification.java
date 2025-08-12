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
    @Column(name = "user_id")
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = "mail")
    private String mail;

    @Column(name = "is_mail_notification_enabled")
    private boolean isMailNotificationEnabled;

    @OneToOne
    @JoinColumn(name = "user_id")
    @MapsId
    private User user;

}