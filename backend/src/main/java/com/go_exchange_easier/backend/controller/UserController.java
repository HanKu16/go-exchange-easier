package com.go_exchange_easier.backend.controller;

import com.go_exchange_easier.backend.annoations.docs.user.*;
import com.go_exchange_easier.backend.dto.details.UserDetails;
import com.go_exchange_easier.backend.dto.exchange.GetUserExchangeResponse;
import com.go_exchange_easier.backend.dto.universityReview.GetUniversityReviewResponse;
import com.go_exchange_easier.backend.dto.user.*;
import com.go_exchange_easier.backend.model.UserCredentials;
import com.go_exchange_easier.backend.service.ExchangeService;
import com.go_exchange_easier.backend.service.UniversityReviewService;
import com.go_exchange_easier.backend.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "Operations related to user.")
public class UserController {

    private final UniversityReviewService universityReviewService;
    private final ExchangeService exchangeService;
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
            Pageable pageable
    ) {
        Page<UserDetails> page = userService.getPage(nick, pageable);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(1, TimeUnit.HOURS))
                .body(page);
    }

    @GetMapping("/{userId}/universityReviews")
    @GetReviewsApiDocs
    public ResponseEntity<List<GetUniversityReviewResponse>> getReviews(
            @PathVariable("userId") Integer userId,
            @AuthenticationPrincipal UserCredentials principal) {
        List<GetUniversityReviewResponse> response = universityReviewService
                .getByAuthorId(userId, principal.getUser().getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}/exchanges")
    @GetExchangesApiDocs
    public ResponseEntity<List<GetUserExchangeResponse>> getExchanges(
            @PathVariable("userId") Integer userId,
            @AuthenticationPrincipal UserCredentials principal) {
        List<GetUserExchangeResponse> response = exchangeService.getByUser(userId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/description")
    @UpdateUserDescriptionApiDoc
    public ResponseEntity<UpdateUserDescriptionResponse> updateDescription(
            @RequestBody @Valid UpdateUserDescriptionRequest request,
            @AuthenticationPrincipal UserCredentials principal) {
        UpdateUserDescriptionResponse response = userService
                .updateDescription(principal.getUser().getId(), request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/homeUniversity")
    @AssignHomeUniversityApiDoc
    public ResponseEntity<AssignHomeUniversityResponse> assignHomeUniversity(
            @RequestBody @Valid AssignHomeUniversityRequest request,
            @AuthenticationPrincipal UserCredentials principal) {
        AssignHomeUniversityResponse response = userService.assignHomeUniversity(
                principal.getUser().getId(), request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/status")
    @UpdateUserStatusApiDoc
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

}
