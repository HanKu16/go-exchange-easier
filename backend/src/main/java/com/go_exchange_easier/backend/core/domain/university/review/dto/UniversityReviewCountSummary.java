package com.go_exchange_easier.backend.core.domain.university.review.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

@Schema(requiredProperties = {"universityId", "count"})
public record UniversityReviewCountSummary(

        Short universityId,
        Integer count

) implements Serializable { }
