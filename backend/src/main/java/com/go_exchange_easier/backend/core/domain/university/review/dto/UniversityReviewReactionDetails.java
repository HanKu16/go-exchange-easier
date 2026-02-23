package com.go_exchange_easier.backend.core.domain.university.review.dto;

import com.go_exchange_easier.backend.core.domain.reaction.ReactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

@Schema(requiredProperties = {"reactionType", "count"})
public record UniversityReviewReactionDetails(

        ReactionType reactionType,
        Short count

) implements Serializable { }
