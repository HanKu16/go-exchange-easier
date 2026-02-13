package com.go_exchange_easier.backend.core.domain.auth.impl;

import com.go_exchange_easier.backend.core.domain.auth.AuthApi;
import com.go_exchange_easier.backend.core.domain.auth.AuthService;
import com.go_exchange_easier.backend.core.domain.auth.UserRegistrar;
import com.go_exchange_easier.backend.core.domain.auth.dto.LoginRequest;
import com.go_exchange_easier.backend.core.domain.auth.dto.RegistrationRequest;
import com.go_exchange_easier.backend.core.domain.auth.dto.RegistrationSummary;
import com.go_exchange_easier.backend.core.domain.auth.dto.TokenBundle;
import com.go_exchange_easier.backend.core.infrastracture.security.config.JwtConfig;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;
import java.net.URI;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final UserRegistrar userRegistrar;
    private final AuthService authService;
    private final JwtConfig jwtConfig;

    @Override
    public ResponseEntity<RegistrationSummary> register(
            RegistrationRequest request) {
        RegistrationSummary response = userRegistrar.register(request);
        URI locationUri = URI.create("/api/users/" + response.userId());
        return ResponseEntity.created(locationUri).body(response);
    }

    @Override
    public ResponseEntity<Void> login(LoginRequest request, String deviceId,
            String deviceName, HttpServletRequest servletRequest) {
        TokenBundle tokenBundle = authService.login(request, servletRequest);
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

    @Override
    public ResponseEntity<Void> logout(String refreshToken) {
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
    public ResponseEntity<Void> refresh(String refreshToken, String deviceId,
            String deviceName, HttpServletRequest servletRequest) {
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
