package com.go_exchange_easier.backend.annoations.docs.user;

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
@Operation(summary = "Get user basic info")
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "User was successfully returned"),
        @ApiResponse(
                responseCode = "401",
                description = "Authentication failed",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ApiErrorResponse.class))),
        @ApiResponse(
                responseCode = "404",
                description = "User of given id was not found",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ApiErrorResponse.class)))
})
public @interface GetMeApiDocs { }
