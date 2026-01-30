package com.go_exchange_easier.backend.domain.university;

import com.go_exchange_easier.backend.common.dto.Listing;
import com.go_exchange_easier.backend.common.dto.error.ApiErrorResponse;
import com.go_exchange_easier.backend.domain.auth.entity.UserCredentials;
import com.go_exchange_easier.backend.domain.university.dto.*;
import com.go_exchange_easier.backend.domain.university.review.dto.UniversityReviewCountSummary;
import com.go_exchange_easier.backend.domain.university.review.dto.UniversityReviewDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/universities")
@Tag(name = "University")
public interface UniversityApi {

    @GetMapping
    @Operation(summary = "Get page of universities")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Universities were successfully returned"
            )
    })
    ResponseEntity<Page<UniversityDetails>> getPage(
            @ParameterObject @ModelAttribute UniversityFilters filters,
            @ParameterObject Pageable pageable);

    @GetMapping("/{universityId}/profile")
    @Operation(summary = "Get university profile")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Profile was successfully returned"),
            @ApiResponse(
                    responseCode = "404",
                    description = "University of given id was not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    ResponseEntity<UniversityProfile> getProfile(
            @PathVariable Integer universityId,
            @AuthenticationPrincipal UserCredentials principal);

    @GetMapping("/{universityId}/reviews")
    @Operation(summary = "Get reviews about particular university")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Reviews were successfully returned"),
    })
    ResponseEntity<Listing<UniversityReviewDetails>> getReviews(
            @PathVariable Integer universityId,
            @AuthenticationPrincipal UserCredentials principal,
            int page, int size);

    @GetMapping("/{universityId}/reviews/count")
    @Operation(summary = "Get count of reviews about particular university")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Count was successfully returned")
    })
    ResponseEntity<UniversityReviewCountSummary> getCount(
            @PathVariable Integer universityId);

}
