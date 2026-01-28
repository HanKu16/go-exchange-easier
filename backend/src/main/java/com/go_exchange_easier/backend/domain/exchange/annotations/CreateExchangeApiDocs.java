package com.go_exchange_easier.backend.domain.exchange.annotations;

import com.go_exchange_easier.backend.common.dto.error.ApiErrorResponse;
import com.go_exchange_easier.backend.domain.exchange.dto.CreateExchangeResponse;
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
@Operation(summary = "Create exchange")
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "201",
                description = "Exchange was successfully created",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = CreateExchangeResponse.class))),
        @ApiResponse(
                responseCode = "400",
                description = "Validation failed - invalid request body",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ApiErrorResponse.class))),
        @ApiResponse(
                responseCode = "422",
                description = "There is something wrong with resources that " +
                        "are referenced in request body",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ApiErrorResponse.class)))
})
public @interface CreateExchangeApiDocs { }
