package com.go_exchange_easier.backend.core.domain.report.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface UserReportRepository extends
        JpaRepository<UserReport, UUID> { }
