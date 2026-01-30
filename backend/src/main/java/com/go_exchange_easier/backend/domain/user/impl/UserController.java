package com.go_exchange_easier.backend.domain.user.impl;

import com.go_exchange_easier.backend.domain.location.country.CountryDetails;
import com.go_exchange_easier.backend.domain.university.dto.UniversitySummary;
import com.go_exchange_easier.backend.domain.user.UserApi;
import com.go_exchange_easier.backend.domain.user.UserService;
import com.go_exchange_easier.backend.domain.user.avatar.AvatarUrlSummary;
import com.go_exchange_easier.backend.domain.user.description.UpdateUserDescriptionRequest;
import com.go_exchange_easier.backend.domain.user.description.UserDescriptionDetails;
import com.go_exchange_easier.backend.domain.user.dto.*;
import com.go_exchange_easier.backend.common.dto.Listing;
import com.go_exchange_easier.backend.domain.university.dto.UniversityDetails;
import com.go_exchange_easier.backend.domain.university.review.dto.UniversityReviewDetails;
import com.go_exchange_easier.backend.domain.auth.entity.UserCredentials;
import com.go_exchange_easier.backend.domain.university.review.UniversityReviewService;
import com.go_exchange_easier.backend.domain.user.avatar.ValidAvatar;
import com.go_exchange_easier.backend.domain.user.status.UpdateUserStatusRequest;
import com.go_exchange_easier.backend.domain.user.status.UserStatusSummary;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.CacheControl;
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
public class UserController implements UserApi {

    private final UniversityReviewService universityReviewService;
    private final UserService userService;

    @Override
    public ResponseEntity<UserProfileDetails> getProfile(
            @PathVariable("userId") int userId,
            @AuthenticationPrincipal UserCredentials principal) {
        UserProfileDetails response = userService.getProfile(
                userId, principal.getUser().getId());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Page<UserDetails>> getPage(
            @RequestParam(value = "nick", required = false) String nick,
            Pageable pageable) {
        Page<UserDetails> page = userService.getPage(nick, pageable);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(1, TimeUnit.HOURS))
                .body(page);
    }

    @Override
    public ResponseEntity<Listing<UniversityReviewDetails>> getReviews(
            @PathVariable("userId") Integer userId,
            @AuthenticationPrincipal UserCredentials principal) {
        List<UniversityReviewDetails> reviews = universityReviewService
                .getByAuthorId(userId, principal.getUser().getId());
        return ResponseEntity.ok(Listing.of(reviews));
    }

    @Override
    public ResponseEntity<UserDescriptionDetails> updateDescription(
            @RequestBody @Valid UpdateUserDescriptionRequest request,
            @AuthenticationPrincipal UserCredentials principal) {
        UserDescriptionDetails response = userService
                .updateDescription(principal.getUser().getId(), request);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<UniversitySummary> assignHomeUniversity(
            @RequestBody @Valid AssignHomeUniversityRequest request,
            @AuthenticationPrincipal UserCredentials principal) {
        UniversitySummary university = userService.assignHomeUniversity(
                principal.getUser().getId(), request);
        return ResponseEntity.ok(university);
    }

    @Override
    public ResponseEntity<UserStatusSummary> updateStatus(
            @RequestBody @Valid UpdateUserStatusRequest request,
            @AuthenticationPrincipal UserCredentials principal) {
        UserStatusSummary status = userService.updateStatus(
                principal.getUser().getId(), request);
        return ResponseEntity.ok(status);
    }

    @Override
    public ResponseEntity<CountryDetails> assignCountryOfOrigin(
            @RequestBody @Valid AssignCountryOfOriginRequest request,
            @AuthenticationPrincipal UserCredentials principal) {
        CountryDetails country = userService.assignCountryOfOrigin(
                principal.getUser().getId(), request);
        return ResponseEntity.ok(country);
    }

    @Override
    public ResponseEntity<Listing<UserWithAvatarSummary>> getFollowees(
            @PathVariable Integer userId) {
        List<UserWithAvatarSummary> followees = userService.getFollowees(userId);
        return ResponseEntity.ok(Listing.of(followees));
    }

    @Override
    public ResponseEntity<Listing<UniversityDetails>> getFollowedUniversities(
            @PathVariable Integer userId) {
        List<UniversityDetails> universities = userService
                .getFollowedUniversities(userId);
        return ResponseEntity.ok(Listing.of(universities));
    }

    @Override
    public ResponseEntity<?> getMe(
            @AuthenticationPrincipal(errorOnInvalidType = false) UserCredentials principal) {
        if (principal == null) {
            return ResponseEntity.status(401).build();
        }
        UserWithAvatarSummary user = userService.getMe(principal.getUser().getId());
        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity<AvatarUrlSummary> uploadAvatar(
            @RequestParam("file") @ValidAvatar MultipartFile file,
            @AuthenticationPrincipal UserCredentials principal) {
        AvatarUrlSummary avatarUrl = userService.addAvatar(
                principal.getUser().getId(), file);
        return ResponseEntity.ok(avatarUrl);
    }

    @Override
    public ResponseEntity<AvatarUrlSummary> deleteAvatar(
            @AuthenticationPrincipal UserCredentials principal) {
        AvatarUrlSummary avatarUrl = userService.deleteAvatar(
                principal.getUser().getId());
        return ResponseEntity.ok(avatarUrl);
    }

}
