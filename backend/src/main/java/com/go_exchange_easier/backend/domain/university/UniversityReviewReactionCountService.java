package com.go_exchange_easier.backend.domain.university;

import com.go_exchange_easier.backend.domain.university.dto.UniversityReviewReactionDetail;

import java.util.List;

public interface UniversityReviewReactionCountService {

    List<UniversityReviewReactionDetail> decrement(int reviewId, short reactionTypeId);
    List<UniversityReviewReactionDetail> increment(int reviewId, short reactionTypeId);
    List<UniversityReviewReactionDetail> replace(int reviewId,
            short oldReactionTypeId, short newReactionTypeId);
    List<UniversityReviewReactionDetail> createCounts(UniversityReview review);
    void deleteCounts(List<UniversityReviewReactionCount> counts);

}
