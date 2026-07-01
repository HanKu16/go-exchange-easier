package com.go_exchange_easier.backend.core.domain.report.user.dto;

import com.go_exchange_easier.backend.core.domain.report.ReportReason;
import com.go_exchange_easier.backend.core.domain.report.user.UserReport;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import java.time.OffsetDateTime;
import java.util.UUID;

@Schema(requiredProperties = {"id", "createdAt", "reason"})
public record UserReportSummary(
        UUID id,
        OffsetDateTime createdAt,

        @Nullable
        String description,

        ReportReason reason
) {

    public static UserReportSummary fromEntity(UserReport report) {
        return new UserReportSummary(
                report.getId(),
                report.getCreatedAt(),
                report.getDescription(),
                report.getReason()
        );
    }

}