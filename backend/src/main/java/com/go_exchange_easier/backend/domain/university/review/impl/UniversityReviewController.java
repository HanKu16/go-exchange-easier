package com.go_exchange_easier.backend.domain.university.review.impl;

import com.go_exchange_easier.backend.domain.auth.entity.UserCredentials;
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
            @AuthenticationPrincipal UserCredentials principal) {
        UniversityReviewDetails review = universityReviewService
                .create(principal.getUser().getId(), request);
        return ResponseEntity.created(URI.create("/api/universityReviews" + review.id()))
                .body(review);
    }

    @Override
    public ResponseEntity<Void> delete(
            @PathVariable Integer reviewId,
            @AuthenticationPrincipal UserCredentials principal) {
        universityReviewService.delete(reviewId, principal.getUser().getId());
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<AddUniversityReviewReactionResponse> addReaction(
            @PathVariable Integer reviewId,
            @RequestBody @Valid AddUniversityReviewReactionRequest request,
            @AuthenticationPrincipal UserCredentials principal) {
        AddUniversityReviewReactionResponse response = reviewReactionService
                .add(principal.getUser().getId(), reviewId,request);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<DeleteUniversityReviewReactionResponse> deleteReaction(
            @PathVariable Integer reviewId,
            @AuthenticationPrincipal UserCredentials principal) {
        DeleteUniversityReviewReactionResponse response = reviewReactionService
                .delete(principal.getUser().getId(), reviewId);
        return ResponseEntity.ok(response);
    }

}
