package com.go_exchange_easier.backend.domain.university.review;

import com.go_exchange_easier.backend.domain.university.review.entity.UniversityReview;
import com.go_exchange_easier.backend.domain.university.review.dto.UniversityReviewReactionDetail;
import com.go_exchange_easier.backend.domain.university.review.entity.UniversityReviewReactionCount;
import java.util.List;

public interface UniversityReviewReactionCountService {

    List<UniversityReviewReactionDetail> decrement(int reviewId, short reactionTypeId);
    List<UniversityReviewReactionDetail> increment(int reviewId, short reactionTypeId);
    List<UniversityReviewReactionDetail> replace(int reviewId,
            short oldReactionTypeId, short newReactionTypeId);
    List<UniversityReviewReactionDetail> createCounts(UniversityReview review);
    void deleteCounts(List<UniversityReviewReactionCount> counts);

}
