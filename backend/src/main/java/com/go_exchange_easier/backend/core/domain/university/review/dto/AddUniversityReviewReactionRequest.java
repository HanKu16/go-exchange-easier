package com.go_exchange_easier.backend.core.domain.university.review.dto;

import com.go_exchange_easier.backend.core.domain.reaction.ReactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record AddUniversityReviewReactionRequest(

        @NotNull(message = "Reaction type is required.")
        @Schema(example = "like")
        ReactionType reactionType

) { }
