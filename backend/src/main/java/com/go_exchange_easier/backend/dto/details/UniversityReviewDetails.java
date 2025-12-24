package com.go_exchange_easier.backend.dto.details;

import com.go_exchange_easier.backend.dto.summary.UniversitySummary;
import com.go_exchange_easier.backend.dto.summary.UserSummary;
import java.time.Instant;
import java.util.List;

public record UniversityReviewDetails(

        Integer id,
        UserSummary author,
        UniversitySummary university,
        Short starRating,
        String textContent,
        Instant createdAt,
        List<ReactionDetails> reactions

) { }
