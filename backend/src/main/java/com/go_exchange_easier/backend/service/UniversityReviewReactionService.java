package com.go_exchange_easier.backend.service;

import com.go_exchange_easier.backend.dto.university.AddUniversityReviewReactionRequest;
import com.go_exchange_easier.backend.dto.university.AddUniversityReviewReactionResponse;
import com.go_exchange_easier.backend.dto.university.DeleteUniversityReviewReactionResponse;

public interface UniversityReviewReactionService {

    AddUniversityReviewReactionResponse add(int userId, int reviewId,
            AddUniversityReviewReactionRequest request);
    DeleteUniversityReviewReactionResponse delete(int userId, int reviewId);

}
