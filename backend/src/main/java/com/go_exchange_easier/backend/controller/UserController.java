package com.go_exchange_easier.backend.controller;

import com.go_exchange_easier.backend.annoations.docs.user.*;
import com.go_exchange_easier.backend.dto.common.Listing;
import com.go_exchange_easier.backend.dto.details.UniversityDetails;
import com.go_exchange_easier.backend.dto.details.UniversityReviewDetails;
import com.go_exchange_easier.backend.dto.details.UserDetails;
import com.go_exchange_easier.backend.dto.summary.UserSummary;
import com.go_exchange_easier.backend.dto.user.*;
import com.go_exchange_easier.backend.model.UserCredentials;
import com.go_exchange_easier.backend.service.AvatarService;
import com.go_exchange_easier.backend.service.UniversityReviewService;
import com.go_exchange_easier.backend.service.UserService;
import com.go_exchange_easier.backend.validation.ValidAvatar;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Validated
@Tag(name = "User", description = "Operations related to user.")
public class UserController {

    private final UniversityReviewService universityReviewService;
    private final AvatarService avatarService;
    private final UserService userService;

    @GetMapping("/{userId}/profile")
    @GetProfileApiDocs
    public ResponseEntity<GetUserProfileResponse> getProfile(
            @PathVariable("userId") int userId,
            @AuthenticationPrincipal UserCredentials principal) {
        GetUserProfileResponse response = userService.getProfile(
                userId, principal.getUser().getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @GetPageApiDocs
    public ResponseEntity<Page<UserDetails>> getPage(
            @RequestParam(value = "nick", required = false) String nick,
            Pageable pageable) {
        Page<UserDetails> page = userService.getPage(nick, pageable);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(1, TimeUnit.HOURS))
                .body(page);
    }

    @GetMapping("/{userId}/universityReviews")
    @GetReviewsApiDocs
    public ResponseEntity<Listing<UniversityReviewDetails>> getReviews(
            @PathVariable("userId") Integer userId,
            @AuthenticationPrincipal UserCredentials principal) {
        List<UniversityReviewDetails> reviews = universityReviewService
                .getByAuthorId(userId, principal.getUser().getId());
        return ResponseEntity.ok(Listing.of(reviews));
    }

    @PatchMapping("/description")
    @UpdateDescriptionApiDocs
    public ResponseEntity<UpdateUserDescriptionResponse> updateDescription(
            @RequestBody @Valid UpdateUserDescriptionRequest request,
            @AuthenticationPrincipal UserCredentials principal) {
        UpdateUserDescriptionResponse response = userService
                .updateDescription(principal.getUser().getId(), request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/homeUniversity")
    @AssignHomeUniversityApiDocs
    public ResponseEntity<AssignHomeUniversityResponse> assignHomeUniversity(
            @RequestBody @Valid AssignHomeUniversityRequest request,
            @AuthenticationPrincipal UserCredentials principal) {
        AssignHomeUniversityResponse response = userService.assignHomeUniversity(
                principal.getUser().getId(), request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/status")
    @UpdateStatusApiDocs
    public ResponseEntity<UpdateUserStatusResponse> updateStatus(
            @RequestBody @Valid UpdateUserStatusRequest request,
            @AuthenticationPrincipal UserCredentials principal) {
        UpdateUserStatusResponse response = userService.updateStatus(
                principal.getUser().getId(), request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/countryOfOrigin")
    @AssignCountryOfOriginApiDocs
    public ResponseEntity<AssignCountryOfOriginResponse> assignCountryOfOrigin(
            @RequestBody @Valid AssignCountryOfOriginRequest request,
            @AuthenticationPrincipal UserCredentials principal) {
        AssignCountryOfOriginResponse response = userService.assignCountryOfOrigin(
                principal.getUser().getId(), request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}/followees")
    @GetFolloweesApiDocs
    public ResponseEntity<Listing<UserSummary>> getFollowees(
            @PathVariable Integer userId) {
        List<UserSummary> followees = userService.getFollowees(userId);
        return ResponseEntity.ok(Listing.of(followees));
    }

    @GetMapping("/{userId}/followedUniversities")
    @GetFollowedUniversitiesApiDocs
    public ResponseEntity<Listing<UniversityDetails>> getFollowedUniversities(
            @PathVariable Integer userId) {
        List<UniversityDetails> universities = userService
                .getFollowedUniversities(userId);
        return ResponseEntity.ok(Listing.of(universities));
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMe(
            @AuthenticationPrincipal(errorOnInvalidType = false) UserCredentials principal) {
        if (principal == null) {
            return ResponseEntity.status(401).build();
        }
        UserSummary user = userService.getMe(principal.getUser().getId());
        return ResponseEntity.ok(user);
    }

    @PostMapping(value = "/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AvatarUrlSummary> uploadAvatar(
            @RequestParam("file") @ValidAvatar MultipartFile file,
            @AuthenticationPrincipal UserCredentials principal) {
        AvatarUrlSummary avatarUrl = userService.addAvatar(principal.getUser().getId(), file);
        return ResponseEntity.ok(avatarUrl);
    }

    @DeleteMapping("/avatar")
    public ResponseEntity<AvatarUrlSummary> deleteAvatar(
            @AuthenticationPrincipal UserCredentials principal) {
        AvatarUrlSummary avatarUrl = userService.deleteAvatar(principal.getUser().getId());
        return ResponseEntity.ok(avatarUrl);
    }

}
