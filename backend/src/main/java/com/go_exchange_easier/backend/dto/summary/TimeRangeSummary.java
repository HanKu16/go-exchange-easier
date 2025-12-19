package com.go_exchange_easier.backend.dto.summary;

import java.time.LocalDate;

public record TimeRangeSummary(

        LocalDate startedAt,
        LocalDate endAt

) { }
