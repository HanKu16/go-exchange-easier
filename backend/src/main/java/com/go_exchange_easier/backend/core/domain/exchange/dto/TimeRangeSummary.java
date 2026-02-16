package com.go_exchange_easier.backend.core.domain.exchange.dto;

import java.io.Serializable;
import java.time.LocalDate;

public record TimeRangeSummary(

        LocalDate startedAt,
        LocalDate endAt

) implements Serializable { }
