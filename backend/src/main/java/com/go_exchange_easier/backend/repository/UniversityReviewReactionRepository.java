package com.go_exchange_easier.backend.repository;

import com.go_exchange_easier.backend.model.UniversityReviewReaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UniversityReviewReactionRepository extends
        JpaRepository<UniversityReviewReaction, Long> {

    Optional<UniversityReviewReaction> findByAuthorIdAndReviewId(
            int authorId, int reviewId);

}
