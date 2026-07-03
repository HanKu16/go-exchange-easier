package com.go_exchange_easier.backend.core.domain.university.review;

import com.go_exchange_easier.backend.core.domain.university.review.entity.UniversityReviewReaction;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UniversityReviewReactionRepository extends
        JpaRepository<UniversityReviewReaction, Long> {

    Optional<UniversityReviewReaction> findByAuthorIdAndReviewId(UUID authorId, int reviewId);

}
