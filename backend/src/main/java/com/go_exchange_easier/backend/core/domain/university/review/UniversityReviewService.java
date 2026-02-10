package com.go_exchange_easier.backend.core.domain.university.review;

import com.go_exchange_easier.backend.core.domain.university.review.dto.UniversityReviewDetails;
import com.go_exchange_easier.backend.core.domain.university.review.dto.UniversityReviewCountSummary;
import com.go_exchange_easier.backend.core.domain.university.review.dto.CreateUniversityReviewRequest;
import java.util.List;

public interface UniversityReviewService {

    List<UniversityReviewDetails> getByAuthorId(int authorId, int currentUserId);
    List<UniversityReviewDetails> getByUniversityId(int universityId,
            int currentUserId, int page, int size);
    UniversityReviewDetails create(int userId, CreateUniversityReviewRequest request);
    void delete(int reviewId, int userId);
    UniversityReviewCountSummary countByUniversityId(int universityId);

}
