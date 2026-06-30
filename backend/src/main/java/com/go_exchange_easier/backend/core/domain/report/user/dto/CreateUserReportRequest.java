package com.go_exchange_easier.backend.core.domain.report.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

public record CreateUserReportRequest(

        @Size(max = 1000, message = "Description cannot be longer than 1000 characters.")
        @Schema(example = "Profile photo of this user violates rules.")
        String description

) { }
