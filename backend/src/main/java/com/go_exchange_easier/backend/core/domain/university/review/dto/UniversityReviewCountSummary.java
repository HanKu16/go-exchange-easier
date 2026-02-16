package com.go_exchange_easier.backend.core.domain.university.review.dto;

import java.io.Serializable;

public record UniversityReviewCountSummary(

        Short universityId,
        Integer count

) implements Serializable { }
