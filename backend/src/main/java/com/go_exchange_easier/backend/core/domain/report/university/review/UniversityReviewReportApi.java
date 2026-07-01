package com.go_exchange_easier.backend.core.domain.report.university.review;

import com.go_exchange_easier.backend.common.dto.error.ApiErrorResponse;
import com.go_exchange_easier.backend.core.domain.auth.dto.AuthenticatedUser;
import com.go_exchange_easier.backend.core.domain.report.university.review.dto.CreateUniversityReviewReportRequest;
import com.go_exchange_easier.backend.core.domain.report.university.review.dto.UniversityReviewReportSummary;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/reports/university-reviews")
@Tag(name = "University review report")
public interface UniversityReviewReportApi {

    @PostMapping("/{reviewId}")
    @Operation(summary = "Create university review report")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "University review report was successfully created"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation failed - invalid request body",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Review was not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    ResponseEntity<UniversityReviewReportSummary> create(
            @PathVariable("reviewId") Integer reviewId,
            @RequestBody @Valid CreateUniversityReviewReportRequest request,
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser);

}