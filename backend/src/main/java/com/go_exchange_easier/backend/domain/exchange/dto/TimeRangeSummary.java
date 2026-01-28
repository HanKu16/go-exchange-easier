package com.go_exchange_easier.backend.domain.exchange.dto;

import java.time.LocalDate;

public record TimeRangeSummary(

        LocalDate startedAt,
        LocalDate endAt

) { }
