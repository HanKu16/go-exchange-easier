package com.go_exchange_easier.backend.controller;

import com.go_exchange_easier.backend.annoations.docs.university.GetPageApiDocs;
import com.go_exchange_easier.backend.annoations.docs.university.GetCountApiDocs;
import com.go_exchange_easier.backend.annoations.docs.university.GetProfileApiDocs;
import com.go_exchange_easier.backend.annoations.docs.university.GetReviewsApiDocs;
import com.go_exchange_easier.backend.dto.common.Listing;
import com.go_exchange_easier.backend.dto.details.UniversityDetails;
import com.go_exchange_easier.backend.dto.details.UniversityReviewDetails;
import com.go_exchange_easier.backend.dto.filter.UniversityFilters;
import com.go_exchange_easier.backend.dto.summary.UniversityReviewCountSummary;
import com.go_exchange_easier.backend.dto.university.UniversityProfile;
import com.go_exchange_easier.backend.model.UserCredentials;
import com.go_exchange_easier.backend.service.UniversityReviewService;
import com.go_exchange_easier.backend.service.UniversityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/universities")
@RequiredArgsConstructor
@Tag(name = "University", description = "Operations related to universities.")
public class UniversityController {

    private final UniversityReviewService universityReviewService;
    private final UniversityService universityService;

    @GetMapping
    @GetPageApiDocs
    public ResponseEntity<Page<UniversityDetails>> getPage(
            @ParameterObject @ModelAttribute UniversityFilters filters,
            @ParameterObject Pageable pageable) {
        Page<UniversityDetails> page = universityService
                .getPage(filters, pageable);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(60, TimeUnit.MINUTES))
                .body(page);
    }

    @GetMapping("/{universityId}/profile")
    @GetProfileApiDocs
    public ResponseEntity<UniversityProfile> getProfile(
            @PathVariable Integer universityId,
            @AuthenticationPrincipal UserCredentials principal) {
        UniversityProfile profile = universityService.getProfile(
                universityId, principal.getUser().getId());
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/{universityId}/reviews")
    @GetReviewsApiDocs
    public ResponseEntity<Listing<UniversityReviewDetails>> getReviews(
            @PathVariable Integer universityId,
            @AuthenticationPrincipal UserCredentials principal,
            int page, int size) {
        List<UniversityReviewDetails> reviews = universityReviewService
                .getByUniversityId(universityId, principal.getUser().getId(),
                        page, size);
        return ResponseEntity.ok(Listing.of(reviews));
    }

    @GetMapping("/{universityId}/reviews/count")
    @GetCountApiDocs
    public ResponseEntity<UniversityReviewCountSummary> getCount(
            @PathVariable Integer universityId) {
        UniversityReviewCountSummary reviewCount = universityReviewService
                .countByUniversityId(universityId);
        return ResponseEntity.ok(reviewCount);
    }

}
