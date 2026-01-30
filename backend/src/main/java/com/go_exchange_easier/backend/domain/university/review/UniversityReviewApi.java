package com.go_exchange_easier.backend.domain.university.review;

import com.go_exchange_easier.backend.common.dto.error.ApiErrorResponse;
import com.go_exchange_easier.backend.domain.auth.entity.UserCredentials;
import com.go_exchange_easier.backend.domain.university.review.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/universityReviews")
@Tag(name = "University review")
public interface UniversityReviewApi {

    @PostMapping
    @Operation(summary = "Create university review")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "University review was successfully created"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation failed - invalid request body",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(
                    responseCode = "422",
                    description = "There is something wrong with resources that " +
                            "are referenced in request body",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    ResponseEntity<UniversityReviewDetails> create(
            @RequestBody @Valid CreateUniversityReviewRequest request,
            @AuthenticationPrincipal UserCredentials principal);

    @DeleteMapping("/{reviewId}")
    @Operation(summary = "Delete university review")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "University review was successfully deleted"),
            @ApiResponse(
                    responseCode = "403",
                    description = "User was trying to delete review that he is not owner of",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "University review of given id was not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))),
    })
    ResponseEntity<Void> delete(
            @PathVariable Integer reviewId,
            @AuthenticationPrincipal UserCredentials principal);

    @PutMapping("/{reviewId}/reaction")
    @Operation(summary = "Create university review reaction")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Reaction was successfully added"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation failed - invalid request body",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(
                    responseCode = "422",
                    description = "There is something wrong with resources that " +
                            "are referenced in request body",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    ResponseEntity<Void> addReaction(
            @PathVariable Integer reviewId,
            @RequestBody @Valid AddUniversityReviewReactionRequest request,
            @AuthenticationPrincipal UserCredentials principal);

    @DeleteMapping("/{reviewId}/reaction")
    @Operation(summary = "Delete university review reaction")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Reaction was successfully deleted"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Reaction was not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))),
    })
    ResponseEntity<Void> deleteReaction(
            @PathVariable Integer reviewId,
            @AuthenticationPrincipal UserCredentials principal);

}
