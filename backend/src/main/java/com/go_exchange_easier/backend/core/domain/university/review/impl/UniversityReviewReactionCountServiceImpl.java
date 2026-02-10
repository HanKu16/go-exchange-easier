package com.go_exchange_easier.backend.core.domain.university.review.impl;

import com.go_exchange_easier.backend.core.domain.reaction.ReactionType;
import com.go_exchange_easier.backend.core.domain.university.review.entity.UniversityReview;
import com.go_exchange_easier.backend.core.domain.university.review.dto.UniversityReviewReactionDetails;
import com.go_exchange_easier.backend.core.domain.university.review.UniversityReviewReactionCountRepository;
import com.go_exchange_easier.backend.core.domain.university.review.UniversityReviewReactionCountService;
import com.go_exchange_easier.backend.core.domain.university.review.entity.UniversityReviewReactionCount;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UniversityReviewReactionCountServiceImpl implements
        UniversityReviewReactionCountService {

    private final UniversityReviewReactionCountRepository reactionCountRepository;

    @Override
    @Transactional
    public List<UniversityReviewReactionDetails> decrement(
            int reviewId, ReactionType reactionType) {
        List<UniversityReviewReactionCount> allCountsForReview =
                getAllCountsForReview(reviewId);
        UniversityReviewReactionCount countForType = getCountOfReactionType(
                    allCountsForReview, reactionType, reviewId);
        decrementCountValue(countForType);
        return mapToReactionDetails(allCountsForReview);
    }

    @Override
    @Transactional
    public List<UniversityReviewReactionDetails> increment(
            int reviewId, ReactionType reactionType) {
        List<UniversityReviewReactionCount> allCountsForReview =
                getAllCountsForReview(reviewId);
        UniversityReviewReactionCount countForType = getCountOfReactionType(
                allCountsForReview, reactionType, reviewId);
        incrementCountValue(countForType);
        return mapToReactionDetails(allCountsForReview);
    }

    @Override
    @Transactional
    public List<UniversityReviewReactionDetails> replace(
            int reviewId, ReactionType oldReactionType,
            ReactionType newReactionType) {
        List<UniversityReviewReactionCount> allCountsForReview =
                getAllCountsForReview(reviewId);
        UniversityReviewReactionCount countForOldType = getCountOfReactionType(
                allCountsForReview, oldReactionType, reviewId);
        UniversityReviewReactionCount countForNewType = getCountOfReactionType(
                allCountsForReview, newReactionType, reviewId);
        decrementCountValue(countForOldType);
        incrementCountValue(countForNewType);
        return mapToReactionDetails(allCountsForReview);
    }

    @Override
    @Transactional
    public List<UniversityReviewReactionDetails> createCounts(UniversityReview review) {
        short defaultCountValue = (short) 0;
        List<UniversityReviewReactionCount> countsForReview = new ArrayList<>();
        for (ReactionType reactionType : ReactionType.values()) {
            UniversityReviewReactionCount count = new UniversityReviewReactionCount();
            count.setReview(review);
            count.setType(reactionType);
            count.setCount(defaultCountValue);
            countsForReview.add(count);
        }
        List<UniversityReviewReactionCount> savedCounts =
                reactionCountRepository.saveAll(countsForReview);
        return mapToReactionDetails(savedCounts);
    }

    @Override
    @Transactional
    public void deleteCounts(List<UniversityReviewReactionCount> counts) {
        reactionCountRepository.deleteAll(counts);
    }

    private List<UniversityReviewReactionCount> getAllCountsForReview(int reviewId) {
        return reactionCountRepository.findAllByReviewId(reviewId);
    }

    private UniversityReviewReactionCount getCountOfReactionType(
            List<UniversityReviewReactionCount> allCountsForReview,
            ReactionType reactionType, int reviewId) {
        return allCountsForReview
                .stream()
                .filter(c -> c.getType() == reactionType)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        "University review reaction where review id " + reviewId +
                                " and reaction type " + reactionType +
                                " was not found. There is probably some issue in logic" +
                                " check if review of id" + reviewId + " exists. If yes" +
                                " all counts for this review should also exist."));
    }

    private List<UniversityReviewReactionDetails> mapToReactionDetails(
            List<UniversityReviewReactionCount> allCountsForReview) {
        return allCountsForReview
                .stream()
                .map(c -> new UniversityReviewReactionDetails(
                        c.getType(), c.getCount()))
                .toList();
    }

    private void incrementCountValue(UniversityReviewReactionCount count) {
        short newCount = (short) (count.getCount() + 1);
        count.setCount(newCount);
        reactionCountRepository.save(count);
    }

    private void decrementCountValue(UniversityReviewReactionCount count) {
        short newCount = (short) (count.getCount() - 1);
        count.setCount(newCount);
        reactionCountRepository.save(count);
    }

}
