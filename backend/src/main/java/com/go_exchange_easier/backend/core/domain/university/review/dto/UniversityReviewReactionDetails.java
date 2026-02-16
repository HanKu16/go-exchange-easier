package com.go_exchange_easier.backend.core.domain.university.review.dto;

import com.go_exchange_easier.backend.core.domain.reaction.ReactionType;
import java.io.Serializable;

public record UniversityReviewReactionDetails(

        ReactionType reactionType,
        Short count

) implements Serializable { }
