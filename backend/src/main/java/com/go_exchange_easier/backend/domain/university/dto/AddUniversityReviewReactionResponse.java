package com.go_exchange_easier.backend.domain.university.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Response body for adding university review reaction")
public record AddUniversityReviewReactionResponse(

        Integer universityReviewId,
        List<UniversityReviewReactionDetail> reactionDetails

) { }
