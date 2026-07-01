package com.go_exchange_easier.backend.core.domain.report.university.review.impl;

import com.go_exchange_easier.backend.core.domain.auth.dto.AuthenticatedUser;
import com.go_exchange_easier.backend.core.domain.report.university.review.UniversityReviewReportApi;
import com.go_exchange_easier.backend.core.domain.report.university.review.UniversityReviewReportService;
import com.go_exchange_easier.backend.core.domain.report.university.review.dto.CreateUniversityReviewReportRequest;
import com.go_exchange_easier.backend.core.domain.report.university.review.dto.UniversityReviewReportSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UniversityReviewReportController implements UniversityReviewReportApi {

    private final UniversityReviewReportService universityReviewReportService;

    public ResponseEntity<UniversityReviewReportSummary> create(
            Integer reviewId,
            CreateUniversityReviewReportRequest request,
            AuthenticatedUser authenticatedUser
    ) {
        UniversityReviewReportSummary report = universityReviewReportService.create(
                reviewId,
                authenticatedUser.getId(),
                request
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(report);
    }

}
