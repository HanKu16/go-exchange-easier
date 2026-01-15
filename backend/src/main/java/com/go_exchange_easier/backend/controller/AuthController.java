package com.go_exchange_easier.backend.controller;

import com.go_exchange_easier.backend.annoations.docs.auth.LoginApiDocs;
import com.go_exchange_easier.backend.annoations.docs.auth.LogoutApiDocs;
import com.go_exchange_easier.backend.annoations.docs.auth.RefreshApiDocs;
import com.go_exchange_easier.backend.annoations.docs.auth.RegisterApiDocs;
import com.go_exchange_easier.backend.dto.auth.LoginRequest;
import com.go_exchange_easier.backend.dto.auth.TokenBundle;
import com.go_exchange_easier.backend.dto.auth.UserRegistrationRequest;
import com.go_exchange_easier.backend.dto.auth.UserRegistrationResponse;
import com.go_exchange_easier.backend.dto.summary.LoginSummary;
import com.go_exchange_easier.backend.dto.summary.SignedInUserSummary;
import com.go_exchange_easier.backend.model.UserCredentials;
import com.go_exchange_easier.backend.security.JwtConfig;
import com.go_exchange_easier.backend.service.AuthService;
import com.go_exchange_easier.backend.service.UserRegistrar;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
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
    private final JwtConfig jwtConfig;

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
    public ResponseEntity<SignedInUserSummary> login(
            @RequestBody @Valid LoginRequest request,
            HttpServletRequest servletRequest) {
        LoginSummary loginSummary = authService.login(request, servletRequest);
        TokenBundle tokenBundle = loginSummary.tokenBundle();
        ResponseCookie accessTokenCookie = ResponseCookie.from(
                "accessToken", tokenBundle.accessToken())
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(jwtConfig.getAccessTokenValidityInSeconds())
                .sameSite("Lax")
                .build();
        ResponseCookie refreshTokenCookie = ResponseCookie.from(
                        "refreshToken", tokenBundle.refreshToken())
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(jwtConfig.getRefreshTokenValidityInSeconds())
                .sameSite("Lax")
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(loginSummary.signedInUserSummary());
    }

    @PostMapping("/logout")
    @LogoutApiDocs
    public ResponseEntity<Void> logout(
            @AuthenticationPrincipal UserCredentials principal,
            @CookieValue(name = "refreshToken") String refreshToken) {
        TokenBundle tokenBundle = authService.logout(refreshToken);
        ResponseCookie accessTokenCookie = ResponseCookie.from(
                        "accessToken", tokenBundle.accessToken())
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
                .build();
        ResponseCookie refreshTokenCookie = ResponseCookie.from(
                        "refreshToken", tokenBundle.refreshToken())
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
                .build();
        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .build();
    }

    @PostMapping("/refresh")
    @RefreshApiDocs
    public ResponseEntity<Void> refresh(
            @CookieValue(name = "refreshToken") String refreshToken,
            HttpServletRequest servletRequest) {
        TokenBundle tokenBundle = authService.refresh(refreshToken, servletRequest);
        ResponseCookie accessTokenCookie = ResponseCookie.from(
                        "accessToken", tokenBundle.accessToken())
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(jwtConfig.getAccessTokenValidityInSeconds())
                .sameSite("Lax")
                .build();
        ResponseCookie refreshTokenCookie = ResponseCookie.from(
                        "refreshToken", tokenBundle.refreshToken())
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(jwtConfig.getRefreshTokenValidityInSeconds())
                .sameSite("Lax")
                .build();
        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .build();
    }

}
