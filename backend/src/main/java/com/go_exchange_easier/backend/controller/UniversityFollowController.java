package com.go_exchange_easier.backend.controller;

import com.go_exchange_easier.backend.annoations.docs.university.CreateUniversityFollowApiDocs;
import com.go_exchange_easier.backend.annoations.docs.university.DeleteUniversityFollowApiDocs;
import com.go_exchange_easier.backend.model.UserCredentials;
import com.go_exchange_easier.backend.service.UniversityFollowService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/universities/{universityId}/follow")
@RequiredArgsConstructor
@Tag(name = "University Follow", description =
        "Operations related to following universities.")
public class UniversityFollowController {

    private final UniversityFollowService universityFollowService;

    @PostMapping
    @CreateUniversityFollowApiDocs
    public ResponseEntity<Void> create(@PathVariable Short universityId,
            @AuthenticationPrincipal UserCredentials principal) {
        universityFollowService.follow(principal.getUser().getId(), universityId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    @DeleteUniversityFollowApiDocs
    public ResponseEntity<Void> delete(@PathVariable Short universityId,
            @AuthenticationPrincipal UserCredentials principal) {
        universityFollowService.unfollow(principal.getUser().getId(), universityId);
        return ResponseEntity.noContent().build();
    }

}
