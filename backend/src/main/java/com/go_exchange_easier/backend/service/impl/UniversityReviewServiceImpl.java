package com.go_exchange_easier.backend.service.impl;

import com.go_exchange_easier.backend.dto.university.CreateUniversityReviewRequest;
import com.go_exchange_easier.backend.dto.university.CreateUniversityReviewResponse;
import com.go_exchange_easier.backend.dto.university.UniversityReviewReactionDetail;
import com.go_exchange_easier.backend.exception.NotOwnerOfResourceException;
import com.go_exchange_easier.backend.exception.base.ReferencedResourceNotFoundException;
import com.go_exchange_easier.backend.exception.domain.UniversityReviewReactionNotFoundException;
import com.go_exchange_easier.backend.model.University;
import com.go_exchange_easier.backend.model.UniversityReview;
import com.go_exchange_easier.backend.model.User;
import com.go_exchange_easier.backend.repository.*;
import com.go_exchange_easier.backend.service.ResourceOwnershipChecker;
import com.go_exchange_easier.backend.service.UniversityReviewReactionCountService;
import com.go_exchange_easier.backend.service.UniversityReviewService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UniversityReviewServiceImpl implements UniversityReviewService {

    private final UniversityReviewReactionCountService reactionCountService;
    private final UniversityReviewRepository universityReviewRepository;
    private final ResourceOwnershipChecker resourceOwnershipChecker;
    private final UniversityRepository universityRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public CreateUniversityReviewResponse create(int userId,
            CreateUniversityReviewRequest request) {
        if (!universityRepository.existsById(request.universityId())) {
            throw new ReferencedResourceNotFoundException("University of id "
                    + request.universityId() + " was not found.");
        }
        User user = userRepository.getReferenceById(userId);
        University university = universityRepository
                .getReferenceById(request.universityId());
        UniversityReview review = buildUniversityReview(request, user, university);
        UniversityReview savedReview = universityReviewRepository.save(review);
        List<UniversityReviewReactionDetail> reactionDetails =
                reactionCountService.createCounts(savedReview);
        return new CreateUniversityReviewResponse(savedReview.getId(),
                savedReview.getCreatedAt());
    }

    @Override
    @Transactional
    public void delete(int reviewId) {
        UniversityReview review = universityReviewRepository.findById(reviewId)
                .orElseThrow(() -> new UniversityReviewReactionNotFoundException(
                        "University " + "review of id " + reviewId + " was not found."));
        if (!resourceOwnershipChecker.isOwner(review)) {
            throw new NotOwnerOfResourceException("Authenticated user is not " +
                    "entitled to delete university review of id " + reviewId + ".");
        }
        reactionCountService.deleteCounts(review.getReactionCounts().stream().toList());
        universityReviewRepository.delete(review);
    }

    private UniversityReview buildUniversityReview(
            CreateUniversityReviewRequest request,
            User user, University university) {
        UniversityReview review = new UniversityReview();
        review.setTextContent(request.text());
        review.setStarRating(request.starRating());
        review.setCreatedAt(OffsetDateTime.now());
        review.setAuthor(user);
        review.setUniversity(university);
        return review;
    }

}
