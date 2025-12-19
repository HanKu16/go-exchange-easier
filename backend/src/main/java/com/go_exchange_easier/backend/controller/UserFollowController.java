package com.go_exchange_easier.backend.controller;

import com.go_exchange_easier.backend.annoations.docs.userFollow.CreateApiDocs;
import com.go_exchange_easier.backend.annoations.docs.userFollow.DeleteApiDocs;
import com.go_exchange_easier.backend.model.UserCredentials;
import com.go_exchange_easier.backend.service.UserFollowService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/{followeeId}/follow")
@RequiredArgsConstructor
@Tag(name = "User Follow", description =
        "Operations related to following users.")
public class UserFollowController {

    private final UserFollowService userFollowService;

    @PostMapping
    @CreateApiDocs
    public ResponseEntity<Void> create(@PathVariable Integer followeeId,
            @AuthenticationPrincipal UserCredentials principal) {
        userFollowService.follow(principal.getUser().getId(), followeeId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    @DeleteApiDocs
    public ResponseEntity<Void> delete(@PathVariable Integer followeeId,
            @AuthenticationPrincipal UserCredentials principal) {
        userFollowService.unfollow(principal.getUser().getId(), followeeId);
        return ResponseEntity.noContent().build();
    }

}
