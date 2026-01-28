package com.go_exchange_easier.backend.domain.university;

import com.go_exchange_easier.backend.domain.user.User;
import com.go_exchange_easier.backend.domain.user.UserRepository;
import com.go_exchange_easier.backend.domain.university.dto.AddUniversityReviewReactionRequest;
import com.go_exchange_easier.backend.domain.university.dto.AddUniversityReviewReactionResponse;
import com.go_exchange_easier.backend.domain.university.dto.DeleteUniversityReviewReactionResponse;
import com.go_exchange_easier.backend.domain.university.dto.UniversityReviewReactionDetail;
import com.go_exchange_easier.backend.common.exception.ReferencedResourceNotFoundException;
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
    private final ReactionTypeRepository reactionTypeRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public AddUniversityReviewReactionResponse add(int userId, int reviewId,
            AddUniversityReviewReactionRequest request) {
        if (!universityReviewRepository.existsById(reviewId)) {
            throw new ReferencedResourceNotFoundException("University review " +
                    "of id " + reviewId + " was not found.");
        }
        if (!reactionTypeRepository.existsById(request.reactionTypeId())) {
            throw new ReferencedResourceNotFoundException("Reaction type " +
                    "of id " + request.reactionTypeId() + " was not found.");
        }
        List<UniversityReviewReactionDetail> reactionDetails;
        Optional<UniversityReviewReaction> oldReaction = reactionRepository
                .findByAuthorIdAndReviewId(userId, reviewId);
        if (oldReaction.isPresent()) {
            short oldReactionTypeId = oldReaction.get().getReactionType().getId();
            reactionDetails = reactionCountService.replace(
                    reviewId, oldReactionTypeId, request.reactionTypeId());
            reactionRepository.delete(oldReaction.get());
        } else {
            reactionDetails = reactionCountService.increment(
                    reviewId, request.reactionTypeId());
        }
        UniversityReviewReaction reaction = buildReaction(
                request.reactionTypeId(), reviewId, userId);
        reactionRepository.save(reaction);
        return new AddUniversityReviewReactionResponse(reviewId, reactionDetails);
    }

    @Override
    @Transactional
    public DeleteUniversityReviewReactionResponse delete(int userId, int reviewId) {
        UniversityReviewReaction reaction = reactionRepository
                .findByAuthorIdAndReviewId(userId, reviewId)
                .orElseThrow(() -> new UniversityReviewReactionNotFoundException(
                        "University review reaction where user id " + userId +
                                " and review id " + reviewId + " was not found."));
        short reactionTypeId = reaction.getReactionType().getId();
        reactionRepository.delete(reaction);
        List<UniversityReviewReactionDetail> reactionDetails =
                reactionCountService.decrement(reviewId, reactionTypeId);
        return new DeleteUniversityReviewReactionResponse(reviewId, reactionDetails);
    }

    private UniversityReviewReaction buildReaction(
            short reactionTypeId, int reviewId, int userId) {
        ReactionType reactionType = reactionTypeRepository
                .getReferenceById(reactionTypeId);
        UniversityReview review = universityReviewRepository
                .getReferenceById(reviewId);
        User user = userRepository.getReferenceById(userId);
        UniversityReviewReaction reaction = new UniversityReviewReaction();
        reaction.setReactionType(reactionType);
        reaction.setReview(review);
        reaction.setAuthor(user);
        return reaction;
    }

}

