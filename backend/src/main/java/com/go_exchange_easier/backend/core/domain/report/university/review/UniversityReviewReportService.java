package com.go_exchange_easier.backend.core.domain.report.university.review;

import com.go_exchange_easier.backend.core.domain.report.university.review.dto.CreateUniversityReviewReportRequest;
import com.go_exchange_easier.backend.core.domain.report.university.review.dto.UniversityReviewReportDetails;

public interface UniversityReviewReportService {

    UniversityReviewReportDetails create(
            int reviewId,
            int reporterId,
            CreateUniversityReviewReportRequest request
    );

}
