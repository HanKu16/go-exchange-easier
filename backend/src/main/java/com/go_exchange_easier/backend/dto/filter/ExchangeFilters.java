package com.go_exchange_easier.backend.dto.filter;

import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;

public record ExchangeFilters(

        @RequestParam(value = "universityId", required = false) Short universityId,
        @RequestParam(value = "cityId", required = false) Integer cityId,
        @RequestParam(value = "countryId", required = false) Short countryId,
        @RequestParam(value = "majorId", required = false) Short majorId,
        @RequestParam(value = "startDate", required = false) LocalDate startDate,
        @RequestParam(value = "endDate", required = false) LocalDate endDate

) {}