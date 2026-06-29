package com.go_exchange_easier.backend.core.domain.report.university;

import com.go_exchange_easier.backend.core.domain.report.university.dto.CreateUniversityReviewReportRequest;
import com.go_exchange_easier.backend.core.domain.report.university.dto.UniversityReviewReportDetails;

public interface UniversityReviewReportService {

    UniversityReviewReportDetails create(int reviewId, CreateUniversityReviewReportRequest request);

}
