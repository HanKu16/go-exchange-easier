package com.go_exchange_easier.backend.domain.university.review;

import com.go_exchange_easier.backend.domain.university.review.dto.AddUniversityReviewReactionRequest;
import com.go_exchange_easier.backend.domain.university.review.dto.AddUniversityReviewReactionResponse;
import com.go_exchange_easier.backend.domain.university.review.dto.DeleteUniversityReviewReactionResponse;

public interface UniversityReviewReactionService {

    AddUniversityReviewReactionResponse add(int userId, int reviewId,
            AddUniversityReviewReactionRequest request);
    DeleteUniversityReviewReactionResponse delete(int userId, int reviewId);

}
