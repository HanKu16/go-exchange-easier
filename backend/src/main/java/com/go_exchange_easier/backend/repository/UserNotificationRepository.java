package com.go_exchange_easier.backend.repository;

import com.go_exchange_easier.backend.model.UserNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserNotificationRepository extends
        JpaRepository<UserNotification, Integer> {

}
