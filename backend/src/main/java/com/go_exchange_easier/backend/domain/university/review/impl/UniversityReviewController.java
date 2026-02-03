package com.go_exchange_easier.backend.domain.university.review.impl;

import com.go_exchange_easier.backend.domain.auth.dto.AuthenticatedUser;
import com.go_exchange_easier.backend.domain.university.review.UniversityReviewApi;
import com.go_exchange_easier.backend.domain.university.review.UniversityReviewReactionService;
import com.go_exchange_easier.backend.domain.university.review.UniversityReviewService;
import com.go_exchange_easier.backend.domain.university.review.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

@RestController
@RequiredArgsConstructor
public class UniversityReviewController implements UniversityReviewApi {

    private final UniversityReviewService universityReviewService;
    private final UniversityReviewReactionService reviewReactionService;

    @Override
    public ResponseEntity<UniversityReviewDetails> create(
            @RequestBody @Valid CreateUniversityReviewRequest request,
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
        UniversityReviewDetails review = universityReviewService
                .create(authenticatedUser.getId(), request);
        return ResponseEntity.created(URI.create("/api/universityReviews" + review.id()))
                .body(review);
    }

    @Override
    public ResponseEntity<Void> delete(
            @PathVariable Integer reviewId,
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
        universityReviewService.delete(reviewId, authenticatedUser.getId());
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> addReaction(
            @PathVariable Integer reviewId,
            @RequestBody @Valid AddUniversityReviewReactionRequest request,
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
        reviewReactionService.add(authenticatedUser.getId(), reviewId,request);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteReaction(
            @PathVariable Integer reviewId,
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
        reviewReactionService.delete(authenticatedUser.getId(), reviewId);
        return ResponseEntity.noContent().build();
    }

}
