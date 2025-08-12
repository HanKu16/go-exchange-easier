package com.go_exchange_easier.backend.service.impl;

import com.go_exchange_easier.backend.dto.university.UniversityReviewReactionDetail;
import com.go_exchange_easier.backend.model.ReactionType;
import com.go_exchange_easier.backend.model.UniversityReview;
import com.go_exchange_easier.backend.model.UniversityReviewReactionCount;
import com.go_exchange_easier.backend.repository.ReactionTypeRepository;
import com.go_exchange_easier.backend.repository.UniversityReviewReactionCountRepository;
import com.go_exchange_easier.backend.service.UniversityReviewReactionCountService;
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
    private final ReactionTypeRepository reactionTypeRepository;

    @Override
    @Transactional
    public List<UniversityReviewReactionDetail> decrement(
            int reviewId, short reactionTypeId) {
        List<UniversityReviewReactionCount> allCountsForReview =
                getAllCountsForReview(reviewId);
        UniversityReviewReactionCount countForType = getCountOfReactionType(
                    allCountsForReview, reactionTypeId, reviewId);
        decrementCountValue(countForType);
        return mapToReactionDetails(allCountsForReview);
    }

    @Override
    @Transactional
    public List<UniversityReviewReactionDetail> increment(
            int reviewId, short reactionTypeId) {
        List<UniversityReviewReactionCount> allCountsForReview =
                getAllCountsForReview(reviewId);
        UniversityReviewReactionCount countForType = getCountOfReactionType(
                allCountsForReview, reactionTypeId, reviewId);
        incrementCountValue(countForType);
        return mapToReactionDetails(allCountsForReview);
    }

    @Override
    @Transactional
    public List<UniversityReviewReactionDetail> replace(int reviewId,
            short oldReactionTypeId, short newReactionTypeId) {
        List<UniversityReviewReactionCount> allCountsForReview =
                getAllCountsForReview(reviewId);
        UniversityReviewReactionCount countForOldType = getCountOfReactionType(
                allCountsForReview, oldReactionTypeId, reviewId);
        UniversityReviewReactionCount countForNewType = getCountOfReactionType(
                allCountsForReview, newReactionTypeId, reviewId);
        decrementCountValue(countForOldType);
        incrementCountValue(countForNewType);
        return mapToReactionDetails(allCountsForReview);
    }

    @Override
    @Transactional
    public List<UniversityReviewReactionDetail> createCounts(UniversityReview review) {
        short defaultCountValue = (short) 0;
        List<UniversityReviewReactionCount> countsForReview = new ArrayList<>();

        List<ReactionType> allReactionTypes = reactionTypeRepository.findAll();
        for (ReactionType reactionType : allReactionTypes) {
            UniversityReviewReactionCount count = new UniversityReviewReactionCount();
            count.setReview(review);
            count.setReactionType(reactionType);
            count.setCount(defaultCountValue);
            countsForReview.add(count);
        }
        List<UniversityReviewReactionCount> savedCounts =
                reactionCountRepository.saveAll(countsForReview);
        return mapToReactionDetails(savedCounts);
    }

    @Override
    public void deleteCounts(List<UniversityReviewReactionCount> counts) {
        reactionCountRepository.deleteAll(counts);
    }

    private List<UniversityReviewReactionCount> getAllCountsForReview(int reviewId) {
        return reactionCountRepository.findAllByReviewId(reviewId);
    }

    private UniversityReviewReactionCount getCountOfReactionType(
            List<UniversityReviewReactionCount> allCountsForReview,
            short reactionTypeId, int reviewId) {
        return allCountsForReview
                .stream()
                .filter(c -> c.getReactionType().getId() == reactionTypeId)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        "University review reaction where review id " + reviewId +
                                " and reaction type id " + reactionTypeId +
                                " was not found. There is probably some issue in logic" +
                                " check if review of id" + reviewId + " exists. If yes" +
                                " all counts for this review should also exist."));
    }

    private List<UniversityReviewReactionDetail> mapToReactionDetails(
            List<UniversityReviewReactionCount> allCountsForReview) {
        return allCountsForReview
                .stream()
                .map(c -> new UniversityReviewReactionDetail(
                        c.getReactionType().getId(), c.getCount()))
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
