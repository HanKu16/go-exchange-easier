package com.go_exchange_easier.backend.core.domain.user.notification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserNotificationRepository extends
        JpaRepository<UserNotification, Integer> {

    boolean existsByMail(String mail);

}
