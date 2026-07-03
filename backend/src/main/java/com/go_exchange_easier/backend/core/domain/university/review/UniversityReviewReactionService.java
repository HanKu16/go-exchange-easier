package com.go_exchange_easier.backend.core.domain.university.review;

import com.go_exchange_easier.backend.core.domain.university.review.dto.AddUniversityReviewReactionRequest;
import com.go_exchange_easier.backend.core.domain.university.review.dto.UniversityReviewReactionDetails;
import java.util.List;
import java.util.UUID;

public interface UniversityReviewReactionService {

    List<UniversityReviewReactionDetails> add(
            UUID userId,
            int reviewId,
            AddUniversityReviewReactionRequest request
    );

    List<UniversityReviewReactionDetails> delete(
            UUID userId,
            int reviewId
    );

}
