package com.go_exchange_easier.backend.repository;

import com.go_exchange_easier.backend.model.UniversityReviewReactionCount;
import com.go_exchange_easier.backend.model.keys.UniversityReviewsReactionCountId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UniversityReviewReactionCountRepository extends
        JpaRepository<UniversityReviewReactionCount,
                UniversityReviewsReactionCountId> {

    List<UniversityReviewReactionCount> findAllByReviewId(int reviewId);
    Optional<UniversityReviewReactionCount> findByReviewIdAndReactionTypeId(
            int reviewId, short reactionTypeId);

}
