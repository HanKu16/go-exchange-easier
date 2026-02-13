package com.go_exchange_easier.backend.core.domain.follow.user.impl;

import com.go_exchange_easier.backend.core.domain.auth.dto.AuthenticatedUser;
import com.go_exchange_easier.backend.core.domain.follow.user.UserFollowApi;
import com.go_exchange_easier.backend.core.domain.follow.user.UserFollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserFollowController implements UserFollowApi {

    private final UserFollowService userFollowService;

    @Override
    public ResponseEntity<Void> create(Integer followeeId,
            AuthenticatedUser authenticatedUser) {
        userFollowService.follow(authenticatedUser.getId(), followeeId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> delete(Integer followeeId,
            AuthenticatedUser authenticatedUser) {
        userFollowService.unfollow(authenticatedUser.getId(), followeeId);
        return ResponseEntity.noContent().build();
    }

}
