package com.go_exchange_easier.backend.core.domain.report.chat.dto;

import com.go_exchange_easier.backend.core.domain.report.ReportReason;
import com.go_exchange_easier.backend.core.domain.report.chat.ChatReport;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import java.time.OffsetDateTime;
import java.util.UUID;

@Schema(requiredProperties = {"id", "createdAt", "reason"})
public record ChatReportSummary(
        UUID id,
        OffsetDateTime createdAt,

        @Nullable
        String description,

        ReportReason reason
) {

    public static ChatReportSummary fromEntity(ChatReport report) {
        return new ChatReportSummary(
                report.getId(),
                report.getCreatedAt(),
                report.getDescription(),
                report.getReason()
        );
    }

}
