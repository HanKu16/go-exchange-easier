package com.go_exchange_easier.backend.domain.university.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Request body for creating university review")
public record CreateUniversityReviewRequest(

        @NotNull(message = "Text is required.")
        @Size(max = 1000, message = "Text max size is 1000 characters.")
        @Schema(example = "This university was real great. I like teaching staff " +
                "like also the campus.")
        String text,

        @NotNull(message = "Star rating is required.")
        @Min(value = 1, message = "Star rating has to be in scope 1 to 5.")
        @Max(value = 5, message = "Star rating has to be in scope 1 to 5.")
        @Schema(example = "4")
        Short starRating,

        @NotNull(message = "University id is required.")
        @Schema(example = "13")
        Short universityId

) { }
