package com.go_exchange_easier.backend.core.domain.location.country;

import com.go_exchange_easier.backend.core.common.dto.Listing;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/countries")
@Tag(name = "Country")
public interface CountryApi {

    @GetMapping
    @Operation(summary = "Get all countries")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Countries were successfully returned"),
    })
    ResponseEntity<Listing<CountryDetails>> getAll();

}
