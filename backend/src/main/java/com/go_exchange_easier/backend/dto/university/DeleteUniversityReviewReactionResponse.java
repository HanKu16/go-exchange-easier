package com.go_exchange_easier.backend.dto.university;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Request body for deleting university review reaction")
public record DeleteUniversityReviewReactionResponse(

        Integer universityReviewId,
        List<UniversityReviewReactionDetail> reactionDetails

) { }
