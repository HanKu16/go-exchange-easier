package com.go_exchange_easier.backend.domain.university.dto;

import com.go_exchange_easier.backend.domain.user.dto.UserWithAvatarSummary;
import java.time.Instant;
import java.util.List;

public record UniversityReviewDetails(

        Integer id,
        UserWithAvatarSummary author,
        UniversitySummary university,
        Short starRating,
        String textContent,
        Instant createdAt,
        List<ReactionDetails> reactions

) { }
