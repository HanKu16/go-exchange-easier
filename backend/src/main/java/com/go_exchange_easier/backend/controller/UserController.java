package com.go_exchange_easier.backend.controller;

import com.go_exchange_easier.backend.annoations.docs.user.AssignHomeUniversityApiDoc;
import com.go_exchange_easier.backend.annoations.docs.user.UpdateUserDescriptionApiDoc;
import com.go_exchange_easier.backend.annoations.docs.user.UpdateUserStatusApiDoc;
import com.go_exchange_easier.backend.dto.user.*;
import com.go_exchange_easier.backend.model.UserCredentials;
import com.go_exchange_easier.backend.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "Operations related to user.")
public class UserController {

    private final UserService userService;

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

}
