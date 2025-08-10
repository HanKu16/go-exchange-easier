package com.go_exchange_easier.backend.service;

import com.go_exchange_easier.backend.dto.university.UniversityReviewReactionDetail;
import java.util.List;

public interface UniversityReviewReactionCountService {

    List<UniversityReviewReactionDetail> decrement(int reviewId, short reactionTypeId);
    List<UniversityReviewReactionDetail> increment(int reviewId, short reactionTypeId);
    List<UniversityReviewReactionDetail> replace(int reviewId,
            short oldReactionTypeId, short newReactionTypeId);

}
