package com.go_exchange_easier.backend.core.domain.university.review.dto;

import com.go_exchange_easier.backend.core.domain.reaction.ReactionDetails;
import com.go_exchange_easier.backend.core.domain.university.dto.UniversityDetails;
import com.go_exchange_easier.backend.core.domain.user.dto.UserWithAvatarSummary;
import java.time.Instant;
import java.util.List;

public record UniversityReviewDetails(

        Integer id,
        UserWithAvatarSummary author,
        UniversityDetails university,
        Short starRating,
        String textContent,
        Instant createdAt,
        List<ReactionDetails> reactions

) { }
