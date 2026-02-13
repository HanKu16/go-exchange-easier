package com.go_exchange_easier.backend.core.domain.follow.university.impl;

import com.go_exchange_easier.backend.core.domain.auth.dto.AuthenticatedUser;
import com.go_exchange_easier.backend.core.domain.follow.university.UniversityFollowApi;
import com.go_exchange_easier.backend.core.domain.follow.university.UniversityFollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UniversityFollowController implements UniversityFollowApi {

    private final UniversityFollowService universityFollowService;

    @Override
    public ResponseEntity<Void> create(Short universityId,
            AuthenticatedUser authenticatedUser) {
        universityFollowService.follow(authenticatedUser.getId(), universityId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> delete(Short universityId,
            AuthenticatedUser authenticatedUser) {
        universityFollowService.unfollow(authenticatedUser.getId(), universityId);
        return ResponseEntity.noContent().build();
    }

}
