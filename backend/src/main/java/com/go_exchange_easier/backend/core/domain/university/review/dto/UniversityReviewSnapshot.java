package com.go_exchange_easier.backend.core.domain.university.review.dto;

import com.go_exchange_easier.backend.core.domain.university.dto.UniversitySummary;
import com.go_exchange_easier.backend.core.domain.user.dto.UserSummary;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.OffsetDateTime;

@Schema(requiredProperties = {"id", "author", "university",
        "starRating", "textContent", "createdAt"})
public record UniversityReviewSnapshot(
        Integer id,
        UserSummary author,
        UniversitySummary university,
        Short starRating,
        String textContent,
        OffsetDateTime createdAt
) { }
