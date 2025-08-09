package com.go_exchange_easier.backend.dto.university;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateUniversityReviewRequest(

        @NotNull(message = "Text is required.")
        @Size(max = 1000, message = "Text max size is 1000 characters.")
        String text,

        @NotNull(message = "Star rating is required.")
        @Min(value = 1, message = "Star rating has to be in scope 1 to 5.")
        @Max(value = 5, message = "Star rating has to be in scope 1 to 5.")
        Short starRating,

        @NotNull(message = "University id is required.")
        Short universityId

) { }
