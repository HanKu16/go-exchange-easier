package com.go_exchange_easier.backend.controller;

import com.go_exchange_easier.backend.annoations.docs.university.AddUniversityReviewReactionApiDocs;
import com.go_exchange_easier.backend.annoations.docs.university.CreateUniversityReviewApiDocs;
import com.go_exchange_easier.backend.annoations.docs.university.DeleteUniversityReviewApiDocs;
import com.go_exchange_easier.backend.annoations.docs.university.DeleteUniversityReviewReactionApiDocs;
import com.go_exchange_easier.backend.dto.university.*;
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
@RequestMapping("/api/universityReview")
@RequiredArgsConstructor
@Tag(name = "University Review", description = "Operations related to university review.")
public class UniversityReviewController {

    private final UniversityReviewService universityReviewService;
    private final UniversityReviewReactionService reviewReactionService;

    @PostMapping
    @CreateUniversityReviewApiDocs
    public ResponseEntity<CreateUniversityReviewResponse> create(
            @RequestBody @Valid CreateUniversityReviewRequest request,
            @AuthenticationPrincipal UserCredentials principal) {
        CreateUniversityReviewResponse response = universityReviewService
                .create(principal.getId(), request);
        return ResponseEntity.created(URI.create("/api/universityReview/" +
                        response.universityReviewId())).body(response);
    }

    @DeleteMapping("/{reviewId}")
    @DeleteUniversityReviewApiDocs
    public ResponseEntity<Void> delete(@PathVariable Integer reviewId) {
        universityReviewService.delete(reviewId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{reviewId}/reaction")
    @AddUniversityReviewReactionApiDocs
    public ResponseEntity<AddUniversityReviewReactionResponse> addReaction(
            @PathVariable Integer reviewId,
            @RequestBody @Valid AddUniversityReviewReactionRequest request,
            @AuthenticationPrincipal UserCredentials principal) {
        AddUniversityReviewReactionResponse response = reviewReactionService
                .add(principal.getId(), reviewId,request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{reviewId}/reaction")
    @DeleteUniversityReviewReactionApiDocs
    public ResponseEntity<DeleteUniversityReviewReactionResponse> deleteReaction(
            @PathVariable Integer reviewId,
            @AuthenticationPrincipal UserCredentials principal) {
        DeleteUniversityReviewReactionResponse response = reviewReactionService
                .delete(principal.getId(), reviewId);
        return ResponseEntity.ok(response);
    }

}
