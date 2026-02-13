package com.go_exchange_easier.backend.core.domain.university.review.impl;

import com.go_exchange_easier.backend.core.domain.auth.dto.AuthenticatedUser;
import com.go_exchange_easier.backend.core.domain.university.review.UniversityReviewApi;
import com.go_exchange_easier.backend.core.domain.university.review.UniversityReviewReactionService;
import com.go_exchange_easier.backend.core.domain.university.review.UniversityReviewService;
import com.go_exchange_easier.backend.core.domain.university.review.dto.AddUniversityReviewReactionRequest;
import com.go_exchange_easier.backend.core.domain.university.review.dto.CreateUniversityReviewRequest;
import com.go_exchange_easier.backend.core.domain.university.review.dto.UniversityReviewDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

@RestController
@RequiredArgsConstructor
public class UniversityReviewController implements UniversityReviewApi {

    private final UniversityReviewService universityReviewService;
    private final UniversityReviewReactionService reviewReactionService;

    @Override
    public ResponseEntity<UniversityReviewDetails> create(
            CreateUniversityReviewRequest request,
            AuthenticatedUser authenticatedUser) {
        UniversityReviewDetails review = universityReviewService
                .create(authenticatedUser.getId(), request);
        return ResponseEntity.created(URI.create("/api/universityReviews" + review.id()))
                .body(review);
    }

    @Override
    public ResponseEntity<Void> delete(Integer reviewId,
            AuthenticatedUser authenticatedUser) {
        universityReviewService.delete(reviewId, authenticatedUser.getId());
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> addReaction(Integer reviewId,
            AddUniversityReviewReactionRequest request,
            AuthenticatedUser authenticatedUser) {
        reviewReactionService.add(authenticatedUser.getId(), reviewId,request);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteReaction(Integer reviewId,
            AuthenticatedUser authenticatedUser) {
        reviewReactionService.delete(authenticatedUser.getId(), reviewId);
        return ResponseEntity.noContent().build();
    }

}
