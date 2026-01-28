package com.go_exchange_easier.backend.domain.university;

import com.go_exchange_easier.backend.domain.university.annotations.GetPageApiDocs;
import com.go_exchange_easier.backend.domain.university.annotations.GetCountApiDocs;
import com.go_exchange_easier.backend.domain.university.annotations.GetProfileApiDocs;
import com.go_exchange_easier.backend.domain.university.annotations.GetReviewsApiDocs;
import com.go_exchange_easier.backend.common.dto.Listing;
import com.go_exchange_easier.backend.domain.university.dto.UniversityDetails;
import com.go_exchange_easier.backend.domain.university.dto.UniversityReviewDetails;
import com.go_exchange_easier.backend.domain.university.dto.UniversityFilters;
import com.go_exchange_easier.backend.domain.university.dto.UniversityReviewCountSummary;
import com.go_exchange_easier.backend.domain.university.dto.UniversityProfile;
import com.go_exchange_easier.backend.domain.auth.UserCredentials;
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
