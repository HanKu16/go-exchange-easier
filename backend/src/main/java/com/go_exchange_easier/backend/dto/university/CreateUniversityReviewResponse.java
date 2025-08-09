package com.go_exchange_easier.backend.dto.university;

import java.time.OffsetDateTime;

public record CreateUniversityReviewResponse(

        Integer universityReviewId,
        OffsetDateTime createdAt

) { }
