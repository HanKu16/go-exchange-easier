package com.go_exchange_easier.backend.service;

import com.go_exchange_easier.backend.dto.details.UniversityReviewDetails;
import com.go_exchange_easier.backend.dto.summary.UniversityReviewCountSummary;
import com.go_exchange_easier.backend.dto.universityReview.CreateUniversityReviewRequest;
import java.util.List;

public interface UniversityReviewService {

    List<UniversityReviewDetails> getByAuthorId(int authorId, int currentUserId);
    List<UniversityReviewDetails> getByUniversityId(int universityId,
            int currentUserId, int page, int size);
    UniversityReviewDetails create(int userId, CreateUniversityReviewRequest request);
    void delete(int reviewId);
    UniversityReviewCountSummary countByUniversityId(int universityId);

}
