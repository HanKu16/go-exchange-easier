package com.go_exchange_easier.backend.domain.fieldofstudy;

import com.go_exchange_easier.backend.common.dto.Listing;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/fieldsOfStudy")
@Tag(name = "Field of study")
public interface FieldOfStudyApi {

    @GetMapping
    @Operation(summary = "Get all fields of study")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Fields of study were successfully returned"),
    })
    ResponseEntity<Listing<FieldOfStudySummary>> getAll();

}
