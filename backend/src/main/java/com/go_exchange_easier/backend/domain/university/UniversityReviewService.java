package com.go_exchange_easier.backend.domain.university;

import com.go_exchange_easier.backend.domain.university.dto.UniversityReviewDetails;
import com.go_exchange_easier.backend.domain.university.dto.UniversityReviewCountSummary;
import com.go_exchange_easier.backend.domain.university.dto.CreateUniversityReviewRequest;
import java.util.List;

public interface UniversityReviewService {

    List<UniversityReviewDetails> getByAuthorId(int authorId, int currentUserId);
    List<UniversityReviewDetails> getByUniversityId(int universityId,
            int currentUserId, int page, int size);
    UniversityReviewDetails create(int userId, CreateUniversityReviewRequest request);
    void delete(int reviewId);
    UniversityReviewCountSummary countByUniversityId(int universityId);

}
