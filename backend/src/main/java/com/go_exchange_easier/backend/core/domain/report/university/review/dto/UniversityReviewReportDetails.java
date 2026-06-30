package com.go_exchange_easier.backend.core.domain.report.university.review.dto;

import com.go_exchange_easier.backend.core.domain.report.ReportStatus;
import com.go_exchange_easier.backend.core.domain.report.university.review.UniversityReviewReport;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

@Schema(requiredProperties = {"reportId", "createdAt", "status", "reporterId", "context", "reportedReviewId"})
public record UniversityReviewReportDetails(
        UUID reportId,
        OffsetDateTime createdAt,

        @Nullable
        String description,

        ReportStatus status,
        Integer reporterId,
        Map<String, Object> context,
        Integer reportedReviewId) implements Serializable
{

    public static UniversityReviewReportDetails fromEntity(UniversityReviewReport report) {
        return new UniversityReviewReportDetails(
                report.getReportId(),
                report.getCreatedAt(),
                report.getDescription(),
                report.getStatus(),
                report.getReporterId(),
                report.getContext(),
                report.getReportedReviewId()
        );
    }

}
