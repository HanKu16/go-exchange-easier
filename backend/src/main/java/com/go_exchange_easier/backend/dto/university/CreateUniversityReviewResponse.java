package com.go_exchange_easier.backend.dto.university;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.OffsetDateTime;

@Schema(example = "Response body for creating university review")
public record CreateUniversityReviewResponse(

        Integer universityReviewId,
        OffsetDateTime createdAt

) { }
