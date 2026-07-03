package com.go_exchange_easier.backend.core.domain.exchange.dto;

import java.time.LocalDate;
import java.util.UUID;
import org.springframework.web.bind.annotation.RequestParam;

public record ExchangeFilters(

        @RequestParam(value = "universityId", required = false)
        Short universityId,

        @RequestParam(value = "cityId", required = false)
        Integer cityId,

        @RequestParam(value = "countryId", required = false)
        Short countryId,

        @RequestParam(value = "fieldOfStudyId", required = false)
        Short fieldOfStudyId,

        @RequestParam(value = "startDate", required = false)
        LocalDate startDate,

        @RequestParam(value = "endDate", required = false)
        LocalDate endDate,

        @RequestParam(value = "userId", required = false)
        UUID userId

) { }