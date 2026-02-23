package com.go_exchange_easier.backend.core.domain.university.review.dto;

import com.go_exchange_easier.backend.core.domain.reaction.ReactionDetails;
import com.go_exchange_easier.backend.core.domain.university.dto.UniversityDetails;
import com.go_exchange_easier.backend.core.domain.user.dto.UserWithAvatarSummary;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Schema(requiredProperties = {"id", "author", "university",
        "starRating", "textContent", "createdAt", "reactions"})
public record UniversityReviewDetails(

        Integer id,
        UserWithAvatarSummary author,
        UniversityDetails university,
        Short starRating,
        String textContent,
        Instant createdAt,
        List<ReactionDetails> reactions

) implements Serializable { }
