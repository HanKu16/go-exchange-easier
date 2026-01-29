package com.go_exchange_easier.backend.domain.follow.university.impl;

import com.go_exchange_easier.backend.domain.auth.entity.UserCredentials;
import com.go_exchange_easier.backend.domain.follow.university.UniversityFollowApi;
import com.go_exchange_easier.backend.domain.follow.university.UniversityFollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UniversityFollowController implements UniversityFollowApi {

    private final UniversityFollowService universityFollowService;

    @Override
    public ResponseEntity<Void> create(
            @PathVariable Short universityId,
            @AuthenticationPrincipal UserCredentials principal) {
        universityFollowService.follow(principal.getUser().getId(), universityId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> delete(
            @PathVariable Short universityId,
            @AuthenticationPrincipal UserCredentials principal) {
        universityFollowService.unfollow(principal.getUser().getId(), universityId);
        return ResponseEntity.noContent().build();
    }

}
