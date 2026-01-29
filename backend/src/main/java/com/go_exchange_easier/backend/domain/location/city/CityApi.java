package com.go_exchange_easier.backend.domain.location.city;

import com.go_exchange_easier.backend.common.dto.Listing;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/cities")
@Tag(name = "City")
public interface CityApi {

    @GetMapping
    @Operation(summary = "Get all cities")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cities were successfully returned"),
    })
    ResponseEntity<Listing<CityDetails>> getAll(
            @RequestParam(value = "countryId", required = false) Short countryId);

}
