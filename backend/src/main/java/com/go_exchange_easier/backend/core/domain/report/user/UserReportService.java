package com.go_exchange_easier.backend.core.domain.report.user;

import com.go_exchange_easier.backend.core.domain.report.user.dto.CreateUserReportRequest;
import com.go_exchange_easier.backend.core.domain.report.user.dto.UserReportDetails;

public interface UserReportService {

    UserReportDetails create(
            int reportedUserId,
            int reporterId,
            CreateUserReportRequest request
    );

}
