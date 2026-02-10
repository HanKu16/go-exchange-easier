package com.go_exchange_easier.backend.core.domain.university.review.dto;

import com.go_exchange_easier.backend.core.domain.reaction.ReactionType;

public record UniversityReviewReactionDetails(

        ReactionType reactionType,
        Short count

) { }
