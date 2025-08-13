package com.go_exchange_easier.backend.annoations.docs.exchange;

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
@Operation(summary = "Delete exchange")
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "Exchange was successfully deleted",
                content = @Content(mediaType = "application/json")),
        @ApiResponse(
                responseCode = "400",
                description = "Validation failed - invalid request body",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ApiErrorResponse.class))),
        @ApiResponse(
                responseCode = "403",
                description = "User was trying to delete exchange that he is not owner of",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ApiErrorResponse.class))),
        @ApiResponse(
                responseCode = "404",
                description = "Exchange of given id was not found",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ApiErrorResponse.class))),

})
public @interface DeleteExchangeApiDocs { }
