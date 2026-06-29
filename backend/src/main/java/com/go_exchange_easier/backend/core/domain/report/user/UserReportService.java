package com.go_exchange_easier.backend.core.domain.report.user;

public interface UserReportService {

    UserReportDetails create(int reportedUserId, int reporterId, CreateUserReportRequest request);

}
