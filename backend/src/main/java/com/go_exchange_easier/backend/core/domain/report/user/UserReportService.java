package com.go_exchange_easier.backend.core.domain.report.user;

import com.go_exchange_easier.backend.core.domain.report.user.dto.CreateUserReportRequest;
import com.go_exchange_easier.backend.core.domain.report.user.dto.UserReportSummary;
import java.util.UUID;

public interface UserReportService {

    UserReportSummary create(
            UUID reportedUserId,
            UUID reporterId,
            CreateUserReportRequest request
    );

}
