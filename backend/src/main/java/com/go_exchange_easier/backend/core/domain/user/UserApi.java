package com.go_exchange_easier.backend.core.domain.user;

import com.go_exchange_easier.backend.common.dto.Listing;
import com.go_exchange_easier.backend.common.dto.error.ApiErrorResponse;
import com.go_exchange_easier.backend.core.domain.auth.dto.AuthenticatedUser;
import com.go_exchange_easier.backend.core.domain.location.country.dto.CountrySummary;
import com.go_exchange_easier.backend.core.domain.university.dto.UniversityDetails;
import com.go_exchange_easier.backend.core.domain.university.dto.UniversitySummary;
import com.go_exchange_easier.backend.core.domain.university.review.dto.UniversityReviewDetails;
import com.go_exchange_easier.backend.core.domain.user.avatar.AvatarUrlSummary;
import com.go_exchange_easier.backend.core.domain.user.avatar.ValidAvatar;
import com.go_exchange_easier.backend.core.domain.user.description.UpdateUserDescriptionRequest;
import com.go_exchange_easier.backend.core.domain.user.description.UserDescriptionDetails;
import com.go_exchange_easier.backend.core.domain.user.dto.*;
import com.go_exchange_easier.backend.core.domain.user.status.UpdateUserStatusRequest;
import com.go_exchange_easier.backend.core.domain.user.status.UserStatusSummary;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/users")
@Tag(name = "User")
public interface UserApi {

    @GetMapping("/{userId}/profile")
    @Operation(summary = "Get user profile")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Profile was successfully returned"),
            @ApiResponse(
                    responseCode = "404",
                    description = "User of given id was not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    ResponseEntity<UserProfile> getProfile(
            @PathVariable("userId") Integer userId,
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser);

    @GetMapping
    @Operation(summary = "Get page of users")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Users were successfully returned")
    })
    ResponseEntity<Page<UserDetails>> getPage(
            @RequestParam(value = "nick", required = false) String nick,
            Pageable pageable);

    @GetMapping("/{userId}/universityReviews")
    @Operation(summary = "Get reviews which were written by user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Reviews were successfully returned"),
    })
    ResponseEntity<Listing<UniversityReviewDetails>> getReviews(
            @PathVariable("userId") Integer userId,
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser);

    @PatchMapping("/description")
    @Operation(summary = "Update user description")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Description was updated successfully"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation failed - invalid request body",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(
                    responseCode = "403",
                    description = "User was trying to update description of another user",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "User of given id was not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    ResponseEntity<UserDescriptionDetails> updateDescription(
            @RequestBody @Valid UpdateUserDescriptionRequest request,
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser);

    @PatchMapping("/homeUniversity")
    @Operation(summary = "Assign home university to user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "University was successfully assigned to user"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation failed - invalid request body",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(
                    responseCode = "403",
                    description = "User was trying to assign home university to another user",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "User of given id was not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(
                    responseCode = "422",
                    description = "There is something wrong with resources that " +
                            "are referenced in request body",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    ResponseEntity<UniversitySummary> assignHomeUniversity(
            @RequestBody @Valid AssignHomeUniversityRequest request,
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser);

    @PatchMapping("/status")
    @Operation(summary = "Update user status")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Status associated with user was successfully updated"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation failed - invalid request body",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(
                    responseCode = "403",
                    description = "User was trying to update status of another user",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "User of given id was not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(
                    responseCode = "422",
                    description = "There is something wrong with resources that " +
                            "are referenced in request body",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    ResponseEntity<UserStatusSummary> updateStatus(
            @RequestBody @Valid UpdateUserStatusRequest request,
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser);

    @PatchMapping("/countryOfOrigin")
    @Operation(summary = "Assign country of origin to user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Country was successfully assigned to user"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation failed - invalid request body",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "User of given id was not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(
                    responseCode = "422",
                    description = "There is something wrong with resources that " +
                            "are referenced in request body",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    ResponseEntity<CountrySummary> assignCountryOfOrigin(
            @RequestBody @Valid AssignCountryOfOriginRequest request,
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser);

    @GetMapping("/{userId}/followees")
    @Operation(summary = "Get followees")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Followees were successfully returned"),
            @ApiResponse(
                    responseCode = "404",
                    description = "User was not found, so there are no followees",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    ResponseEntity<Listing<UserWithAvatarSummary>> getFollowees(
            @PathVariable Integer userId);

    @GetMapping("/{userId}/followedUniversities")
    @Operation(summary = "Get followed universities")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Universities were successfully returned"),
            @ApiResponse(
                    responseCode = "404",
                    description = "User was not found, so there are no followed universities",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    ResponseEntity<Listing<UniversityDetails>> getFollowedUniversities(
            @PathVariable Integer userId);

    @GetMapping("/me")
    @Operation(summary = "Get user basic info")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User was successfully returned"),
            @ApiResponse(
                    responseCode = "401",
                    description = "Authentication failed",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "User of given id was not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    ResponseEntity<?> getMe(
            @AuthenticationPrincipal(errorOnInvalidType = false) AuthenticatedUser authenticatedUser);

    @PostMapping(value = "/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload user avatar")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Avatar was successfully uploaded"),
            @ApiResponse(
                    responseCode = "404",
                    description = "User of given id was not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "Failed to upload file",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    ResponseEntity<AvatarUrlSummary> uploadAvatar(
            @RequestParam("file") @ValidAvatar MultipartFile file,
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser);

    @DeleteMapping("/avatar")
    @Operation(summary = "Delete user avatar")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Avatar was successfully deleted"),
            @ApiResponse(
                    responseCode = "404",
                    description = "User of given id was not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    ResponseEntity<AvatarUrlSummary> deleteAvatar(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser);

}
