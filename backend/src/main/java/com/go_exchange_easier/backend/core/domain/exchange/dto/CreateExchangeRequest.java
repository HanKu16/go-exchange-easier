package com.go_exchange_easier.backend.core.domain.exchange.dto;

import com.go_exchange_easier.backend.core.common.validation.ValidDateRange;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@ValidDateRange(startDateField = "startedAt", endDateField = "endAt")
public record CreateExchangeRequest(

        @NotNull(message = "Start date of exchange can not be null. If exchange " +
                "is haven't started yet pick approximate date from the future.")
        @Schema(example = "2025-03-05")
        LocalDate startedAt,

        @NotNull(message = "End date of exchange can not be null. If exchange" +
                "haven't finished yet pick approximate date from the future.")
        @Schema(example = "2025-08-09")
        LocalDate endAt,

        @NotNull(message = "University id can not be null.")
        @Schema(example = "9")
        Short universityId,

        @NotNull(message = "Field of study id can not be null.")
        @Schema(example = "20")
        Short fieldOfStudyId

) { }
