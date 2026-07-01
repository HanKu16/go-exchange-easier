package com.go_exchange_easier.backend.core.domain.report.user.impl;

import com.go_exchange_easier.backend.core.domain.auth.dto.AuthenticatedUser;
import com.go_exchange_easier.backend.core.domain.report.user.UserReportApi;
import com.go_exchange_easier.backend.core.domain.report.user.UserReportService;
import com.go_exchange_easier.backend.core.domain.report.user.dto.CreateUserReportRequest;
import com.go_exchange_easier.backend.core.domain.report.user.dto.UserReportSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserReportController implements UserReportApi {

    private final UserReportService userReportService;

    public ResponseEntity<UserReportSummary> create(
            Integer reportedUserId,
            CreateUserReportRequest request,
            AuthenticatedUser authenticatedUser
    ) {
        UserReportSummary report = userReportService.create(reportedUserId, authenticatedUser.getId(), request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(report);
    }

}
