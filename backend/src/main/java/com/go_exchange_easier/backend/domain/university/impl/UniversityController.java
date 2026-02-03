package com.go_exchange_easier.backend.domain.university.impl;

import com.go_exchange_easier.backend.common.dto.Listing;
import com.go_exchange_easier.backend.domain.auth.dto.AuthenticatedUser;
import com.go_exchange_easier.backend.domain.university.UniversityApi;
import com.go_exchange_easier.backend.domain.university.UniversityService;
import com.go_exchange_easier.backend.domain.university.dto.UniversityDetails;
import com.go_exchange_easier.backend.domain.university.review.dto.UniversityReviewDetails;
import com.go_exchange_easier.backend.domain.university.dto.UniversityFilters;
import com.go_exchange_easier.backend.domain.university.review.dto.UniversityReviewCountSummary;
import com.go_exchange_easier.backend.domain.university.dto.UniversityProfile;
import com.go_exchange_easier.backend.domain.university.review.UniversityReviewService;
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
@RequiredArgsConstructor
public class UniversityController implements UniversityApi {

    private final UniversityReviewService universityReviewService;
    private final UniversityService universityService;

    @Override
    public ResponseEntity<Page<UniversityDetails>> getPage(
            @ParameterObject @ModelAttribute UniversityFilters filters,
            @ParameterObject Pageable pageable) {
        Page<UniversityDetails> page = universityService
                .getPage(filters, pageable);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(60, TimeUnit.MINUTES))
                .body(page);
    }

    @Override
    public ResponseEntity<UniversityProfile> getProfile(
            @PathVariable Integer universityId,
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
        UniversityProfile profile = universityService.getProfile(
                universityId, authenticatedUser.getId());
        return ResponseEntity.ok(profile);
    }

    @Override
    public ResponseEntity<Listing<UniversityReviewDetails>> getReviews(
            @PathVariable Integer universityId,
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            int page, int size) {
        List<UniversityReviewDetails> reviews = universityReviewService
                .getByUniversityId(universityId, authenticatedUser.getId(),
                        page, size);
        return ResponseEntity.ok(Listing.of(reviews));
    }

    @Override
    public ResponseEntity<UniversityReviewCountSummary> getCount(
            @PathVariable Integer universityId) {
        UniversityReviewCountSummary reviewCount = universityReviewService
                .countByUniversityId(universityId);
        return ResponseEntity.ok(reviewCount);
    }

}
