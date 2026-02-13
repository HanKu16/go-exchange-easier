package com.go_exchange_easier.backend.core.domain.university.review.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

public record CreateUniversityReviewRequest(

        @NotBlank(message = "Text can not be null, empty or whitespace only.")
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
