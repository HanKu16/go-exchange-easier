package com.go_exchange_easier.backend.domain.university;

import com.go_exchange_easier.backend.domain.university.dto.AddUniversityReviewReactionRequest;
import com.go_exchange_easier.backend.domain.university.dto.AddUniversityReviewReactionResponse;
import com.go_exchange_easier.backend.domain.university.dto.DeleteUniversityReviewReactionResponse;

public interface UniversityReviewReactionService {

    AddUniversityReviewReactionResponse add(int userId, int reviewId,
            AddUniversityReviewReactionRequest request);
    DeleteUniversityReviewReactionResponse delete(int userId, int reviewId);

}
