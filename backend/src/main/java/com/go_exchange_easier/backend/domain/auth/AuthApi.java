package com.go_exchange_easier.backend.domain.auth;

import com.go_exchange_easier.backend.common.dto.error.ApiErrorResponse;
import com.go_exchange_easier.backend.domain.auth.dto.LoginRequest;
import com.go_exchange_easier.backend.domain.auth.dto.RegistrationRequest;
import com.go_exchange_easier.backend.domain.auth.dto.RegistrationSummary;
import com.go_exchange_easier.backend.domain.auth.entity.UserCredentials;
import com.go_exchange_easier.backend.domain.auth.dto.SignedInUserSummary;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/auth")
@Tag(name = "Authentication and registration")
public interface AuthApi {

    @PostMapping("/register")
    @Operation(summary = "Register new user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "User was registered successfully"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation failed - invalid request body",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(
                    responseCode = "409",
                    description = "Registration failed - user of given login already exists",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    ResponseEntity<RegistrationSummary> register(
            @RequestBody @Valid RegistrationRequest request);

    @PostMapping("/login")
    @Operation(summary = "Login user for the first time")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User successfully signed in"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation failed - invalid request body",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(
                    responseCode = "401",
                    description = "User was not authenticated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    ResponseEntity<SignedInUserSummary> login(
            @RequestBody @Valid LoginRequest request,
            @RequestHeader(value = "X-Device-Id", required = true) String deviceId,
            @RequestHeader(value = "X-Device-Name", required = true) String deviceName,
            HttpServletRequest servletRequest);

    @PostMapping("/logout")
    @Operation(summary = "Log out user from particular device")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "User successfully logout from device")
    })
    ResponseEntity<Void> logout(
            @AuthenticationPrincipal UserCredentials principal,
            @CookieValue(name = "refreshToken", required = false) String refreshToken);

    @PostMapping("/refresh")
    @Operation(summary = "Refresh user authentication tokens")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "New tokens were assigned to user on particular device"),
            @ApiResponse(
                    responseCode = "401",
                    description = "Refresh operation failed, user needs to sign in",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    ResponseEntity<Void> refresh(
            @CookieValue(name = "refreshToken") String refreshToken,
            @RequestHeader(value = "X-Device-Id", required = true) String deviceId,
            @RequestHeader(value = "X-Device-Name", required = true) String deviceName,
            HttpServletRequest servletRequest);

}
