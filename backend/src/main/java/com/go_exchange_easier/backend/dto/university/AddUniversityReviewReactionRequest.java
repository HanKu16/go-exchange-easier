package com.go_exchange_easier.backend.dto.university;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request body for adding university review reaction")
public record AddUniversityReviewReactionRequest(

        @NotNull(message = "Reaction type id is required.")
        @Schema(example = "1")
        Short reactionTypeId

) { }
