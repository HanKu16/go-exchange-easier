package com.go_exchange_easier.backend.dto.university;

import jakarta.validation.constraints.NotNull;

public record AddUniversityReviewReactionRequest(

        @NotNull(message = "Reaction type id is required.")
        Short reactionTypeId

) { }
