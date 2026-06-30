package com.go_exchange_easier.backend.core.domain.report.university.review.impl;

import com.go_exchange_easier.backend.core.domain.report.ReportContextFactory;
import com.go_exchange_easier.backend.core.domain.report.ReportStatus;
import com.go_exchange_easier.backend.core.domain.report.university.review.UniversityReviewReport;
import com.go_exchange_easier.backend.core.domain.report.university.review.UniversityReviewReportRepository;
import com.go_exchange_easier.backend.core.domain.report.university.review.UniversityReviewReportService;
import com.go_exchange_easier.backend.core.domain.report.university.review.dto.CreateUniversityReviewReportRequest;
import com.go_exchange_easier.backend.core.domain.report.university.review.dto.UniversityReviewReportDetails;
import com.go_exchange_easier.backend.core.domain.university.review.UniversityReviewService;
import com.go_exchange_easier.backend.core.domain.university.review.dto.UniversityReviewSnapshot;
import java.time.OffsetDateTime;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UniversityReviewReportServiceImpl implements UniversityReviewReportService {

    private final UniversityReviewReportRepository universityReviewReportRepository;
    private final UniversityReviewService universityReviewService;
    private final ReportContextFactory reportContextFactory;

    @Override
    @Transactional
    public UniversityReviewReportDetails create(
            int reviewId,
            int reporterId,
            CreateUniversityReviewReportRequest request
    ) {
        UniversityReviewReport report = new UniversityReviewReport();
        report.setCreatedAt(OffsetDateTime.now());
        report.setDescription(request.description());
        report.setStatus(ReportStatus.NEW);
        report.setReporterId(reporterId);
        UniversityReviewSnapshot reviewSnapshot = universityReviewService.getSnapshotById(reviewId);
        Map<String, Object> context = reportContextFactory.createFromReviewSnapshot(reviewSnapshot);
        report.setContext(context);
        report.setReportedReviewId(reviewId);
        UniversityReviewReport savedReport = universityReviewReportRepository.save(report);
        return UniversityReviewReportDetails.fromEntity(savedReport);
    }

}

