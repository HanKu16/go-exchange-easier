package com.go_exchange_easier.backend.core.domain.university.review;

import com.go_exchange_easier.backend.core.domain.university.review.entity.UniversityReviewReactionCount;
import com.go_exchange_easier.backend.core.domain.university.review.entity.UniversityReviewsReactionCountId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniversityReviewReactionCountRepository extends JpaRepository<UniversityReviewReactionCount, UniversityReviewsReactionCountId> {

    List<UniversityReviewReactionCount> findAllByReviewId(int reviewId);

}
