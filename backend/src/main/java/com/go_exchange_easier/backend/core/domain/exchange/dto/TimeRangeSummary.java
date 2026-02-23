package com.go_exchange_easier.backend.core.domain.exchange.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDate;

@Schema(requiredProperties = {"startedAt", "endAt"})
public record TimeRangeSummary(

        LocalDate startedAt,
        LocalDate endAt

) implements Serializable { }
