package com.go_exchange_easier.backend.domain.university.review.dto;

import com.go_exchange_easier.backend.domain.reaction.ReactionType;

public record UniversityReviewReactionDetails(

        ReactionType reactionType,
        Short count

) { }
