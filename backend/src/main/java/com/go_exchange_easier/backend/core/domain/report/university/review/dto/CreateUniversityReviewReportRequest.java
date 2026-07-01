package com.go_exchange_easier.backend.core.domain.report.university.review.dto;

import com.go_exchange_easier.backend.core.domain.report.ReportReason;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateUniversityReviewReportRequest(

        @Size(max = 1000, message = "Description cannot be longer than 1000 characters.")
        @Schema(example = "Profile photo of this user violates rules.")
        String description,

        @NotNull(message = "Report reason cannot be null.")
        @Schema(description = "The reason for the report", example = "HARASSMENT")
        ReportReason reason

) { }
