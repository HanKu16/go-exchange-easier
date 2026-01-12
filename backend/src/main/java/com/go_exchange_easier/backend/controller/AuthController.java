package com.go_exchange_easier.backend.controller;

import com.go_exchange_easier.backend.annoations.docs.auth.LoginApiDocs;
import com.go_exchange_easier.backend.annoations.docs.auth.RegisterApiDocs;
import com.go_exchange_easier.backend.dto.auth.LoginRequest;
import com.go_exchange_easier.backend.dto.auth.TokenBundle;
import com.go_exchange_easier.backend.dto.auth.UserRegistrationRequest;
import com.go_exchange_easier.backend.dto.auth.UserRegistrationResponse;
import com.go_exchange_easier.backend.model.UserCredentials;
import com.go_exchange_easier.backend.service.AuthService;
import com.go_exchange_easier.backend.service.UserRegistrar;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Operations related to " +
        "registration and login user for the first time.")
public class AuthController {

    private final UserRegistrar userRegistrar;
    private final AuthService authService;

    @PostMapping("/register")
    @RegisterApiDocs
    public ResponseEntity<UserRegistrationResponse> register(
            @RequestBody @Valid UserRegistrationRequest request) {
        UserRegistrationResponse response = userRegistrar.register(request);
        URI locationUri = URI.create("/api/users/" + response.userId());
        return ResponseEntity.created(locationUri).body(response);
    }

    @PostMapping("/login")
    @LoginApiDocs
    public ResponseEntity<TokenBundle> login(
            @RequestBody @Valid LoginRequest request) {
        TokenBundle response = authService.login(request);
        ResponseCookie accessTokenCookie = ResponseCookie.from(
                "accessToken", response.accessToken())
                .httpOnly(true)
                .secure(false)
//                .secure(true)
                .path("/")
                .maxAge(15 * 60)
                .sameSite("Lax")
//                .sameSite("Strict")
                .build();
        ResponseCookie refreshTokenCookie = ResponseCookie.from(
                        "refreshToken", response.refreshToken())
                .httpOnly(true)
                .secure(false)
//                .secure(true)
                .path("/")
                .maxAge(30 * 24 * 60 * 3600)
                .sameSite("Lax")
//                .sameSite("Strict")
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(response);
    }

    @PostMapping("/logout")
    @LoginApiDocs
    public ResponseEntity<Void> logOut(
            @AuthenticationPrincipal UserCredentials principal) {
        // there will be log out service method called
        // when refresh token will be stored in database
        ResponseCookie accessTokenCookie = ResponseCookie.from(
                        "accessToken", "")
                .httpOnly(true)
                .secure(false)
//                .secure(true)
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
//                .sameSite("Strict")
                .build();
        ResponseCookie refreshTokenCookie = ResponseCookie.from(
                        "refreshToken", "")
                .httpOnly(true)
                .secure(false)
//                .secure(true)
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
//                .sameSite("Strict")
                .build();
        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .build();
    }

}
