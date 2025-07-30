package com.go_exchange_easier.backend.controller;

import com.go_exchange_easier.backend.dto.user.*;
import com.go_exchange_easier.backend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@EnableMethodSecurity
public class UserController {

    private final UserService userService;

    @PatchMapping("/{userId}/description")
    @PreAuthorize("#userId == authentication.principal.id")
    public ResponseEntity<UpdateUserDescriptionResponse> updateDescription(
            @PathVariable Integer userId,
            @RequestBody @Valid UpdateDescriptionRequest request) {
        UpdateUserDescriptionResponse response = userService
                .updateDescription(userId, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{userId}/homeUniversity")
    @PreAuthorize("#userId == authentication.principal.id")
    public ResponseEntity<AssignHomeUniversityResponse> assignHomeUniversity(
            @PathVariable Integer userId,
            @RequestBody @Valid AssignHomeUniversityRequest request) {
        AssignHomeUniversityResponse response = userService.assignHomeUniversity(
                userId, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{userId}/status")
    @PreAuthorize("#userId == authentication.principal.id")
    public ResponseEntity<UpdateUserStatusResponse> updateStatus(
            @PathVariable Integer userId,
            @RequestBody @Valid UpdateUserStatusRequest request) {
        UpdateUserStatusResponse response = userService.updateStatus(userId, request);
        return ResponseEntity.ok(response);
    }

}
