package com.go_exchange_easier.backend.domain.follow.user.impl;

import com.go_exchange_easier.backend.domain.auth.dto.AuthenticatedUser;
import com.go_exchange_easier.backend.domain.follow.user.UserFollowApi;
import com.go_exchange_easier.backend.domain.follow.user.UserFollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserFollowController implements UserFollowApi {

    private final UserFollowService userFollowService;

    @Override
    public ResponseEntity<Void> create(
            @PathVariable Integer followeeId,
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
        userFollowService.follow(authenticatedUser.getId(), followeeId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> delete(
            @PathVariable Integer followeeId,
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
        userFollowService.unfollow(authenticatedUser.getId(), followeeId);
        return ResponseEntity.noContent().build();
    }

}
