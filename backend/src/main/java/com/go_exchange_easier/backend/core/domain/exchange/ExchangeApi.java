package com.go_exchange_easier.backend.core.domain.exchange;

import com.go_exchange_easier.backend.core.common.dto.error.ApiErrorResponse;
import com.go_exchange_easier.backend.core.domain.auth.dto.AuthenticatedUser;
import com.go_exchange_easier.backend.core.domain.exchange.dto.CreateExchangeRequest;
import com.go_exchange_easier.backend.core.domain.exchange.dto.ExchangeDetails;
import com.go_exchange_easier.backend.core.domain.exchange.dto.ExchangeFilters;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/exchanges")
@Tag(name = "Exchange")
public interface ExchangeApi {

    @GetMapping
    @Operation(summary = "Get page of exchanges")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Exchanges were successfully returned")
    })
    ResponseEntity<Page<ExchangeDetails>> getPage(
            @ParameterObject @ModelAttribute ExchangeFilters filters,
            @ParameterObject Pageable pageable);

    @PostMapping
    @Operation(summary = "Create exchange")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Exchange was successfully created",
                    content = @Content(mediaType = "application/json")),
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
    ResponseEntity<ExchangeDetails> create(
            @RequestBody @Valid CreateExchangeRequest request,
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser);

    @DeleteMapping("/{exchangeId}")
    @Operation(summary = "Delete exchange")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Exchange was successfully deleted"),
            @ApiResponse(
                    responseCode = "403",
                    description = "User was trying to delete exchange that he is not owner of",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Exchange of given id was not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    ResponseEntity<Void> delete(
            @PathVariable Integer exchangeId,
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser);

}
