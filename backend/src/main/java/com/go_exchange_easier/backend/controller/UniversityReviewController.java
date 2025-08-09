package com.go_exchange_easier.backend.controller;

import com.go_exchange_easier.backend.dto.university.CreateUniversityReviewRequest;
import com.go_exchange_easier.backend.dto.university.CreateUniversityReviewResponse;
import com.go_exchange_easier.backend.model.UserCredentials;
import com.go_exchange_easier.backend.service.UniversityReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

@RestController
@RequestMapping("/api/universityReview")
@RequiredArgsConstructor
public class UniversityReviewController {

    private final UniversityReviewService universityReviewService;

    @PostMapping
    public ResponseEntity<CreateUniversityReviewResponse> create(
            @RequestBody @Valid CreateUniversityReviewRequest request,
            @AuthenticationPrincipal UserCredentials principal) {
        CreateUniversityReviewResponse response = universityReviewService
                .create(principal.getId(), request);
        return ResponseEntity.created(URI.create("/api/universityReview/" +
                        response.universityReviewId())).body(response);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> delete(@PathVariable Integer reviewId) {
        universityReviewService.delete(reviewId);
        return ResponseEntity.noContent().build();
    }

}
