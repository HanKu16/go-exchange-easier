package com.go_exchange_easier.backend.core.domain.report.user.dto;

import com.go_exchange_easier.backend.core.domain.report.ReportReason;
import com.go_exchange_easier.backend.core.domain.report.ReportStatus;
import com.go_exchange_easier.backend.core.domain.report.ReportType;
import com.go_exchange_easier.backend.core.domain.report.user.UserReport;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

@Schema(requiredProperties = {"id", "createdAt", "status", "reason", "type", "reporterId", "context", "reportedUserId"})
public record UserReportDetails(
        UUID id,
        OffsetDateTime createdAt,

        @Nullable
        String description,

        ReportStatus status,
        ReportReason reason,
        ReportType type,
        UUID reporterId,
        Map<String, Object> context,
        UUID reportedUserId
) implements Serializable {

    public static UserReportDetails fromEntity(UserReport report) {
        return new UserReportDetails(
                report.getId(),
                report.getCreatedAt(),
                report.getDescription(),
                report.getStatus(),
                report.getReason(),
                report.getType(),
                report.getReporterId(),
                report.getContext(),
                report.getReportedUserId()
        );
    }

}
