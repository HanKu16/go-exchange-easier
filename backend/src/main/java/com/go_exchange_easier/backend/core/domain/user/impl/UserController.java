package com.go_exchange_easier.backend.core.domain.user.impl;

import com.go_exchange_easier.backend.core.domain.auth.dto.AuthenticatedUser;
import com.go_exchange_easier.backend.core.domain.location.country.CountrySummary;
import com.go_exchange_easier.backend.core.domain.university.dto.UniversitySummary;
import com.go_exchange_easier.backend.core.domain.user.UserApi;
import com.go_exchange_easier.backend.core.domain.user.UserService;
import com.go_exchange_easier.backend.core.domain.user.avatar.AvatarUrlSummary;
import com.go_exchange_easier.backend.core.domain.user.description.UpdateUserDescriptionRequest;
import com.go_exchange_easier.backend.core.domain.user.description.UserDescriptionDetails;
import com.go_exchange_easier.backend.core.domain.user.dto.*;
import com.go_exchange_easier.backend.common.dto.Listing;
import com.go_exchange_easier.backend.core.domain.university.dto.UniversityDetails;
import com.go_exchange_easier.backend.core.domain.university.review.dto.UniversityReviewDetails;
import com.go_exchange_easier.backend.core.domain.university.review.UniversityReviewService;
import com.go_exchange_easier.backend.core.domain.user.status.UpdateUserStatusRequest;
import com.go_exchange_easier.backend.core.domain.user.status.UserStatusSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor
@Validated
public class UserController implements UserApi {

    private final UniversityReviewService universityReviewService;
    private final UserService userService;

    @Override
    public ResponseEntity<UserProfileDetails> getProfile(
            Integer userId, AuthenticatedUser authenticatedUser) {
        UserProfileDetails response = userService.getProfile(
                userId, authenticatedUser.getId());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Page<UserDetails>> getPage(
            String nick, Pageable pageable) {
        Page<UserDetails> page = userService.getPage(nick, pageable);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(1, TimeUnit.HOURS))
                .body(page);
    }

    @Override
    public ResponseEntity<Listing<UniversityReviewDetails>> getReviews(
            Integer userId, AuthenticatedUser authenticatedUser) {
        List<UniversityReviewDetails> reviews = universityReviewService
                .getByAuthorId(userId, authenticatedUser.getId());
        return ResponseEntity.ok(Listing.of(reviews));
    }

    @Override
    public ResponseEntity<UserDescriptionDetails> updateDescription(
            UpdateUserDescriptionRequest request,
            AuthenticatedUser authenticatedUser) {
        UserDescriptionDetails response = userService
                .updateDescription(authenticatedUser.getId(), request);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<UniversitySummary> assignHomeUniversity(
            AssignHomeUniversityRequest request,
            AuthenticatedUser authenticatedUser) {
        UniversitySummary university = userService.assignHomeUniversity(
                authenticatedUser.getId(), request);
        return ResponseEntity.ok(university);
    }

    @Override
    public ResponseEntity<UserStatusSummary> updateStatus(
            UpdateUserStatusRequest request,
            AuthenticatedUser authenticatedUser) {
        UserStatusSummary status = userService.updateStatus(
                authenticatedUser.getId(), request);
        return ResponseEntity.ok(status);
    }

    @Override
    public ResponseEntity<CountrySummary> assignCountryOfOrigin(
            AssignCountryOfOriginRequest request,
            AuthenticatedUser authenticatedUser) {
        CountrySummary country = userService.assignCountryOfOrigin(
                authenticatedUser.getId(), request);
        return ResponseEntity.ok(country);
    }

    @Override
    public ResponseEntity<Listing<UserWithAvatarSummary>> getFollowees(
            Integer userId) {
        List<UserWithAvatarSummary> followees = userService
                .getFollowees(userId);
        return ResponseEntity.ok(Listing.of(followees));
    }

    @Override
    public ResponseEntity<Listing<UniversityDetails>> getFollowedUniversities(
            Integer userId) {
        List<UniversityDetails> universities = userService
                .getFollowedUniversities(userId);
        return ResponseEntity.ok(Listing.of(universities));
    }

    @Override
    public ResponseEntity<?> getMe(
            AuthenticatedUser authenticatedUser) {
        if (authenticatedUser == null) {
            return ResponseEntity.status(401).build();
        }
        UserWithAvatarSummary user = userService
                .getMe(authenticatedUser.getId());
        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity<AvatarUrlSummary> uploadAvatar(
            MultipartFile file, AuthenticatedUser authenticatedUser) {
        AvatarUrlSummary avatarUrl = userService.addAvatar(
                authenticatedUser.getId(), file);
        return ResponseEntity.ok(avatarUrl);
    }

    @Override
    public ResponseEntity<AvatarUrlSummary> deleteAvatar(
            AuthenticatedUser authenticatedUser) {
        AvatarUrlSummary avatarUrl = userService.deleteAvatar(
                authenticatedUser.getId());
        return ResponseEntity.ok(avatarUrl);
    }

}
