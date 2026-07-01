package com.go_exchange_easier.backend.core.domain.report.user.dto;

import com.go_exchange_easier.backend.core.domain.report.ReportStatus;
import com.go_exchange_easier.backend.core.domain.report.user.UserReport;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

@Schema(requiredProperties = {"id", "createdAt", "status", "reporterId", "context", "reportedUserId"})
public record UserReportDetails(
        UUID id,
        OffsetDateTime createdAt,

        @Nullable
        String description,

        ReportStatus status,
        Integer reporterId,
        Map<String, Object> context,
        Integer reportedUserId
) implements Serializable {

    public static UserReportDetails fromEntity(UserReport report) {
        return new UserReportDetails(
                report.getId(),
                report.getCreatedAt(),
                report.getDescription(),
                report.getStatus(),
                report.getReporterId(),
                report.getContext(),
                report.getReportedUserId()
        );
    }

}
