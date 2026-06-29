package com.go_exchange_easier.backend.core.domain.report.user.impl;

import com.go_exchange_easier.backend.core.domain.report.ReportContextFactory;
import com.go_exchange_easier.backend.core.domain.report.ReportStatus;
import com.go_exchange_easier.backend.core.domain.report.user.*;
import com.go_exchange_easier.backend.core.domain.report.user.dto.CreateUserReportRequest;
import com.go_exchange_easier.backend.core.domain.report.user.dto.UserReportDetails;
import com.go_exchange_easier.backend.core.domain.user.UserPublicProfileProvider;
import com.go_exchange_easier.backend.core.domain.user.dto.UserPublicProfile;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.OffsetDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserReportServiceImpl implements UserReportService {

    private final UserPublicProfileProvider userPublicProfileProvider;
    private final UserReportRepository userReportRepository;
    private final ReportContextFactory reportContextFactory;

    @Override
    @Transactional
    public UserReportDetails create(int reportedUserId, int reporterId, CreateUserReportRequest request) {
        UserReport report = new UserReport();
        report.setCreatedAt(OffsetDateTime.now());
        report.setDescription(request.description());
        report.setStatus(ReportStatus.NEW);
        report.setReporterId(reporterId);
        report.setReportedUserId(reportedUserId);
        UserPublicProfile profile = userPublicProfileProvider.getProfile(reportedUserId);
        Map<String, Object> context = reportContextFactory.createFromProfile(profile);
        report.setContext(context);
        UserReport savedReport = userReportRepository.save(report);
        return UserReportDetails.fromEntity(savedReport);
    }

}
