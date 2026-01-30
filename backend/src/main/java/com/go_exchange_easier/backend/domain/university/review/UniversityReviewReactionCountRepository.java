package com.go_exchange_easier.backend.domain.university.review;

import com.go_exchange_easier.backend.domain.university.review.entity.UniversityReviewReactionCount;
import com.go_exchange_easier.backend.domain.university.review.entity.UniversityReviewsReactionCountId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UniversityReviewReactionCountRepository extends
        JpaRepository<UniversityReviewReactionCount,
                UniversityReviewsReactionCountId> {

    List<UniversityReviewReactionCount> findAllByReviewId(int reviewId);

}
