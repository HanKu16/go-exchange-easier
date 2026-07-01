package com.go_exchange_easier.backend.core.domain.report.university.review.dto;

import com.go_exchange_easier.backend.core.domain.report.ReportReason;
import com.go_exchange_easier.backend.core.domain.report.university.review.UniversityReviewReport;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import java.time.OffsetDateTime;
import java.util.UUID;

@Schema(requiredProperties = {"id", "createdAt", "reason"})
public record UniversityReviewReportSummary(
        UUID id,
        OffsetDateTime createdAt,

        @Nullable
        String description,

        ReportReason reason
) {

    public static UniversityReviewReportSummary fromEntity(UniversityReviewReport report) {
        return new UniversityReviewReportSummary(
                report.getId(),
                report.getCreatedAt(),
                report.getDescription(),
                report.getReason()
        );
    }

}
