package com.go_exchange_easier.backend.dto.exchange;

import com.go_exchange_easier.backend.validation.ValidDateRange;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@ValidDateRange(startDateField = "startedAt", endDateField = "endAt")
@Schema(description = "Request body for create exchange")
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

        @NotNull(message = "University major id can not be null.")
        @Schema(example = "20")
        Short universityMajorId

) { }
