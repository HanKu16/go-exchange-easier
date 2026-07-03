package com.go_exchange_easier.backend.core.domain.university.review;

import com.go_exchange_easier.backend.core.domain.university.review.dto.CreateUniversityReviewRequest;
import com.go_exchange_easier.backend.core.domain.university.review.dto.UniversityReviewCountSummary;
import com.go_exchange_easier.backend.core.domain.university.review.dto.UniversityReviewDetails;
import com.go_exchange_easier.backend.core.domain.university.review.dto.UniversityReviewSnapshot;
import java.util.List;
import java.util.UUID;

public interface UniversityReviewService {

    UniversityReviewSnapshot getSnapshotById(int reviewId);

    List<UniversityReviewDetails> getByAuthorId(
            UUID authorId,
            UUID currentUserId
    );

    List<UniversityReviewDetails> getByUniversityId(
            int universityId,
            UUID currentUserId,
            int page,
            int size
    );

    UniversityReviewDetails create(
            UUID userId,
            CreateUniversityReviewRequest request
    );

    void delete(
            int reviewId,
            UUID userId
    );

    UniversityReviewCountSummary countByUniversityId(int universityId);

}
