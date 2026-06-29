package com.go_exchange_easier.backend.core.domain.report.user;

import jakarta.validation.constraints.Size;

public record CreateUserReportRequest(

        @Size(max = 1000, message = "Description cannot be longer than 1000 characters.")
        String description

) { }
