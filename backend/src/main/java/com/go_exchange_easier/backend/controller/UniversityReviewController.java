package com.go_exchange_easier.backend.controller;

import com.go_exchange_easier.backend.annoations.docs.universityReview.AddReactionApiDocs;
import com.go_exchange_easier.backend.annoations.docs.universityReview.CreateApiDocs;
import com.go_exchange_easier.backend.annoations.docs.universityReview.DeleteApiDocs;
import com.go_exchange_easier.backend.annoations.docs.universityReview.DeleteReactionApiDocs;
import com.go_exchange_easier.backend.dto.details.UniversityReviewDetails;
import com.go_exchange_easier.backend.dto.universityReview.*;
import com.go_exchange_easier.backend.model.UserCredentials;
import com.go_exchange_easier.backend.service.UniversityReviewReactionService;
import com.go_exchange_easier.backend.service.UniversityReviewService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

@RestController
@RequestMapping("/api/universityReviews")
@RequiredArgsConstructor
@Tag(name = "University Review", description = "Operations related to university review.")
public class UniversityReviewController {

    private final UniversityReviewService universityReviewService;
    private final UniversityReviewReactionService reviewReactionService;

    @PostMapping
    @CreateApiDocs
    public ResponseEntity<UniversityReviewDetails> create(
            @RequestBody @Valid CreateUniversityReviewRequest request,
            @AuthenticationPrincipal UserCredentials principal) {
        UniversityReviewDetails review = universityReviewService
                .create(principal.getUser().getId(), request);
        return ResponseEntity.created(URI.create("/api/universityReviews" + review.id()))
                .body(review);
    }

    @DeleteMapping("/{reviewId}")
    @DeleteApiDocs
    public ResponseEntity<Void> delete(
            @PathVariable Integer reviewId) {
        universityReviewService.delete(reviewId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{reviewId}/reaction")
    @AddReactionApiDocs
    public ResponseEntity<AddUniversityReviewReactionResponse> addReaction(
            @PathVariable Integer reviewId,
            @RequestBody @Valid AddUniversityReviewReactionRequest request,
            @AuthenticationPrincipal UserCredentials principal) {
        AddUniversityReviewReactionResponse response = reviewReactionService
                .add(principal.getUser().getId(), reviewId,request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{reviewId}/reaction")
    @DeleteReactionApiDocs
    public ResponseEntity<DeleteUniversityReviewReactionResponse> deleteReaction(
            @PathVariable Integer reviewId,
            @AuthenticationPrincipal UserCredentials principal) {
        DeleteUniversityReviewReactionResponse response = reviewReactionService
                .delete(principal.getUser().getId(), reviewId);
        return ResponseEntity.ok(response);
    }

}
