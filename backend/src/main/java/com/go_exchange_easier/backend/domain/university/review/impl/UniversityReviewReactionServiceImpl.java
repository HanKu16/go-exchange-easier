package com.go_exchange_easier.backend.domain.university.review.impl;

import com.go_exchange_easier.backend.common.exception.ResourceNotFoundException;
import com.go_exchange_easier.backend.domain.reaction.ReactionType;
import com.go_exchange_easier.backend.domain.university.review.UniversityReviewReactionCountService;
import com.go_exchange_easier.backend.domain.university.review.UniversityReviewReactionRepository;
import com.go_exchange_easier.backend.domain.university.review.UniversityReviewReactionService;
import com.go_exchange_easier.backend.domain.university.review.UniversityReviewRepository;
import com.go_exchange_easier.backend.domain.university.review.entity.UniversityReview;
import com.go_exchange_easier.backend.domain.university.review.entity.UniversityReviewReaction;
import com.go_exchange_easier.backend.domain.user.User;
import com.go_exchange_easier.backend.domain.user.UserRepository;
import com.go_exchange_easier.backend.domain.university.review.dto.AddUniversityReviewReactionRequest;
import com.go_exchange_easier.backend.domain.university.review.dto.UniversityReviewReactionDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UniversityReviewReactionServiceImpl implements UniversityReviewReactionService {

    private final UniversityReviewReactionCountService reactionCountService;
    private final UniversityReviewReactionRepository reactionRepository;
    private final UniversityReviewRepository universityReviewRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public List<UniversityReviewReactionDetails> add(int userId,
            int reviewId, AddUniversityReviewReactionRequest request) {
        List<UniversityReviewReactionDetails> reactionDetails;
        Optional<UniversityReviewReaction> oldReaction = reactionRepository
                .findByAuthorIdAndReviewId(userId, reviewId);
        if (oldReaction.isPresent()) {
            reactionDetails = reactionCountService.replace(
                    reviewId, oldReaction.get().getType(),
                    request.reactionType());
            oldReaction.get().setType(request.reactionType());
            reactionRepository.save(oldReaction.get());
        } else {
            reactionDetails = reactionCountService.increment(
                    reviewId, request.reactionType());
            UniversityReviewReaction reaction = buildReaction(
                    request.reactionType(), reviewId, userId);
            reactionRepository.save(reaction);
        }
        return reactionDetails;
    }

    @Override
    @Transactional
    public List<UniversityReviewReactionDetails> delete(int userId, int reviewId) {
        UniversityReviewReaction reaction = reactionRepository
                .findByAuthorIdAndReviewId(userId, reviewId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "University review reaction where user id " + userId +
                                " and review id " + reviewId + " was not found."));
        ReactionType reactionType = reaction.getType();
        reactionRepository.delete(reaction);
        return reactionCountService.decrement(reviewId, reactionType);
    }

    private UniversityReviewReaction buildReaction(
            ReactionType reactionType, int reviewId, int userId) {
        UniversityReview review = universityReviewRepository
                .getReferenceById(reviewId);
        User user = userRepository.getReferenceById(userId);
        UniversityReviewReaction reaction = new UniversityReviewReaction();
        reaction.setType(reactionType);
        reaction.setReview(review);
        reaction.setAuthor(user);
        return reaction;
    }

}

