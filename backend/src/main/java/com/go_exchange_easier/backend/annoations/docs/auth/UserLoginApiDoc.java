package com.go_exchange_easier.backend.annoations.docs.auth;

import com.go_exchange_easier.backend.dto.auth.LoginResponse;
import com.go_exchange_easier.backend.dto.error.ApiErrorResponse;
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
@Operation(summary = "Login user for the first time to get JWT Bearer Token")
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "User successfully login and token was returned",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = LoginResponse.class))),
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
public @interface UserLoginApiDoc { }
