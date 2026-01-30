package com.go_exchange_easier.backend.domain.university.review;

import com.go_exchange_easier.backend.domain.university.review.dto.AddUniversityReviewReactionRequest;
import com.go_exchange_easier.backend.domain.university.review.dto.UniversityReviewReactionDetails;
import java.util.List;

public interface UniversityReviewReactionService {

    List<UniversityReviewReactionDetails> add(int userId, int reviewId,
            AddUniversityReviewReactionRequest request);
    List<UniversityReviewReactionDetails> delete(int userId, int reviewId);

}
