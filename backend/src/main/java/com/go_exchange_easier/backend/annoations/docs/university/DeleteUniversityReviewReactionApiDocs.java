package com.go_exchange_easier.backend.annoations.docs.university;

import com.go_exchange_easier.backend.dto.error.ApiErrorResponse;
import com.go_exchange_easier.backend.dto.university.DeleteUniversityReviewReactionResponse;
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
@Operation(summary = "Delete university review reaction")
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "Reaction was successfully deleted",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = DeleteUniversityReviewReactionResponse.class))),
        @ApiResponse(
                responseCode = "404",
                description = "Reaction was not found",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ApiErrorResponse.class))),
})
public @interface DeleteUniversityReviewReactionApiDocs { }
