package com.go_exchange_easier.backend.annoations.docs.auth;

import com.go_exchange_easier.backend.dto.error.ApiErrorResponse;
import com.go_exchange_easier.backend.dto.user.UserRegistrationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "Register new user")
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "201",
                description = "User was registered successfully",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = UserRegistrationResponse.class))),
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
public @interface RegisterApiDocs {}

