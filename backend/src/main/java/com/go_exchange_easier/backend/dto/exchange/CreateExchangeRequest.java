package com.go_exchange_easier.backend.dto.exchange;

import com.go_exchange_easier.backend.validation.ValidDateRange;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@ValidDateRange(startDateField = "startedAt", endDateField = "endAt")
public record CreateExchangeRequest(

        @NotNull(message = "Start date of exchange can not be null. If exchange " +
                "is haven't started yet pick approximate date from the future.")
        LocalDate startedAt,

        @NotNull(message = "End date of exchange can not be null. If exchange" +
                "haven't finished yet pick approximate date from the future.")
        LocalDate endAt,

        @NotNull(message = "University id can not be null.")
        Short universityId,

        @NotNull(message = "University major id can not be null.")
        Short universityMajorId

) { }
