package com.go_exchange_easier.backend.core.domain.user.status;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStatusRepository extends
        JpaRepository<UserStatus, Short> { }
