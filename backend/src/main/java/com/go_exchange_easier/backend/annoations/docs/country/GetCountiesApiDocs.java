package com.go_exchange_easier.backend.annoations.docs.country;

import com.go_exchange_easier.backend.dto.country.GetCountryResponse;
import com.go_exchange_easier.backend.dto.universityReview.GetUniversityReviewResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
@Operation(summary = "Get all countries")
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "Countries were successfully returned",
                content = @Content(mediaType = "application/json",
                        array = @ArraySchema(
                                schema = @Schema(implementation = GetCountryResponse.class)))),
})
public @interface GetCountiesApiDocs { }
