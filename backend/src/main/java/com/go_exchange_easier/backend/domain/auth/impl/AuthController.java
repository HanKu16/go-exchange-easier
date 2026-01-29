package com.go_exchange_easier.backend.domain.auth.impl;

import com.go_exchange_easier.backend.domain.auth.AuthApi;
import com.go_exchange_easier.backend.domain.auth.AuthService;
import com.go_exchange_easier.backend.domain.auth.UserRegistrar;
import com.go_exchange_easier.backend.domain.auth.dto.LoginRequest;
import com.go_exchange_easier.backend.domain.auth.dto.TokenBundle;
import com.go_exchange_easier.backend.domain.auth.dto.RegistrationRequest;
import com.go_exchange_easier.backend.domain.auth.dto.RegistrationSummary;
import com.go_exchange_easier.backend.domain.auth.dto.LoginSummary;
import com.go_exchange_easier.backend.domain.auth.entity.UserCredentials;
import com.go_exchange_easier.backend.domain.auth.dto.SignedInUserSummary;
import com.go_exchange_easier.backend.infrastracture.security.config.JwtConfig;
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
@RequiredArgsConstructor

public class AuthController implements AuthApi {

    private final UserRegistrar userRegistrar;
    private final AuthService authService;
    private final JwtConfig jwtConfig;

    @Override
    public ResponseEntity<RegistrationSummary> register(
            @RequestBody @Valid RegistrationRequest request) {
        RegistrationSummary response = userRegistrar.register(request);
        URI locationUri = URI.create("/api/users/" + response.userId());
        return ResponseEntity.created(locationUri).body(response);
    }

    @Override
    public ResponseEntity<SignedInUserSummary> login(
            @RequestBody @Valid LoginRequest request,
            @RequestHeader(value = "X-Device-Id", required = true) String deviceId,
            @RequestHeader(value = "X-Device-Name", required = true) String deviceName,
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

    @Override
    public ResponseEntity<Void> logout(
            @AuthenticationPrincipal UserCredentials principal,
            @CookieValue(name = "refreshToken", required = false) String refreshToken) {
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

    @Override
    public ResponseEntity<Void> refresh(
            @CookieValue(name = "refreshToken") String refreshToken,
            @RequestHeader(value = "X-Device-Id", required = true) String deviceId,
            @RequestHeader(value = "X-Device-Name", required = true) String deviceName,
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
