package com.go_exchange_easier.backend.core.domain.report.university.review;

import com.go_exchange_easier.backend.core.domain.report.university.review.dto.CreateUniversityReviewReportRequest;
import com.go_exchange_easier.backend.core.domain.report.university.review.dto.UniversityReviewReportSummary;

public interface UniversityReviewReportService {

    UniversityReviewReportSummary create(
            int reviewId,
            int reporterId,
            CreateUniversityReviewReportRequest request
    );

}
