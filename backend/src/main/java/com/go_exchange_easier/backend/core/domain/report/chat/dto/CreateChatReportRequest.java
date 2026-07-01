package com.go_exchange_easier.backend.core.domain.report.chat.dto;

import com.go_exchange_easier.backend.core.domain.report.ReportReason;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public record CreateChatReportRequest(

        @Size(max = 1000, message = "Description cannot be longer than 1000 characters.")
        @Schema(example = "Profile photo of this user violates rules.")
        String description,

        @NotNull(message = "Report reason cannot be null.")
        @Schema(description = "The reason for the report", example = "HARASSMENT")
        ReportReason reason,

        @NotNull(message = "Reported user id cannot be null.")
        @Schema(example = "123")
        Integer reportedUserId,

        @NotNull(message = "Room id cannot be null.")
        @Schema(example = "550e8400-e29b-41d4-a716-446655440000")
        UUID roomId

) { }
