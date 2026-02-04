package com.go_exchange_easier.backend.domain.university.review;

import com.go_exchange_easier.backend.domain.reaction.ReactionType;
import com.go_exchange_easier.backend.domain.university.review.entity.UniversityReview;
import com.go_exchange_easier.backend.domain.university.review.dto.UniversityReviewReactionDetails;
import com.go_exchange_easier.backend.domain.university.review.entity.UniversityReviewReactionCount;
import java.util.List;

public interface UniversityReviewReactionCountService {

    List<UniversityReviewReactionDetails> decrement(int reviewId, ReactionType reactionType);
    List<UniversityReviewReactionDetails> increment(int reviewId, ReactionType reactionType);
    List<UniversityReviewReactionDetails> replace(int reviewId,
            ReactionType oldReactionType, ReactionType newReactionType);
    List<UniversityReviewReactionDetails> createCounts(UniversityReview review);
    void deleteCounts(List<UniversityReviewReactionCount> counts);

}
