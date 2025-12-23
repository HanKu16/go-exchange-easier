package com.go_exchange_easier.backend.controller;

import com.go_exchange_easier.backend.annoations.docs.university.GetPageApiDocs;
import com.go_exchange_easier.backend.annoations.docs.university.GetCountApiDocs;
import com.go_exchange_easier.backend.annoations.docs.university.GetProfileApiDocs;
import com.go_exchange_easier.backend.annoations.docs.university.GetReviewsApiDocs;
import com.go_exchange_easier.backend.dto.university.GetReviewsCountResponse;
import com.go_exchange_easier.backend.dto.university.GetUniversityProfileResponse;
import com.go_exchange_easier.backend.dto.university.GetUniversityResponse;
import com.go_exchange_easier.backend.dto.universityReview.GetUniversityReviewResponse;
import com.go_exchange_easier.backend.model.UserCredentials;
import com.go_exchange_easier.backend.service.UniversityReviewService;
import com.go_exchange_easier.backend.service.UniversityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<Page<GetUniversityResponse>> getPage(
            @RequestParam(value = "englishName", required = false) String englishName,
            @RequestParam(value = "nativeName", required = false) String nativeName,
            @RequestParam(value = "cityId", required = false) Integer cityId,
            @RequestParam(value = "countryId", required = false) Short countryId,
            Pageable pageable) {
        Page<GetUniversityResponse> response = universityService
                .getPage(englishName, nativeName, cityId, countryId, pageable);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(60, TimeUnit.MINUTES))
                .body(response);
    }

    @GetMapping("/{universityId}/profile")
    @GetProfileApiDocs
    public ResponseEntity<GetUniversityProfileResponse> getProfile(
            @PathVariable Integer universityId,
            @AuthenticationPrincipal UserCredentials principal) {
        GetUniversityProfileResponse response = universityService.getProfile(
                universityId, principal.getUser().getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{universityId}/reviews")
    @GetReviewsApiDocs
    public ResponseEntity<List<GetUniversityReviewResponse>> getReviews(
            @PathVariable Integer universityId,
            @AuthenticationPrincipal UserCredentials principal,
            int page, int size) {
        List<GetUniversityReviewResponse> response = universityReviewService
                .getByUniversityId(universityId, principal.getUser().getId(),
                        page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{universityId}/reviews/count")
    @GetCountApiDocs
    public ResponseEntity<GetReviewsCountResponse> getCount(
            @PathVariable Integer universityId) {
        GetReviewsCountResponse response = universityReviewService
                .countByUniversityId(universityId);
        return ResponseEntity.ok(response);
    }

}
