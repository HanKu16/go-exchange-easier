package com.go_exchange_easier.backend.annoations.docs.university;

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
@Operation(summary = "Create university follow")
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "Follow was successfully created"),
        @ApiResponse(
                responseCode = "409",
                description = "Follow already exists",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ApiErrorResponse.class))),
        @ApiResponse(
                responseCode = "422",
                description = "There is something wrong with resources that " +
                        "are referenced in request body",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ApiErrorResponse.class)))
})
public @interface CreateUniversityFollowApiDocs { }
