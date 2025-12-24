package com.go_exchange_easier.backend.dto.filter;

import org.springframework.web.bind.annotation.RequestParam;

public record UniversityFilters(

        @RequestParam(value = "englishName", required = false) String englishName,
        @RequestParam(value = "nativeName", required = false) String nativeName,
        @RequestParam(value = "cityId", required = false) Integer cityId,
        @RequestParam(value = "countryId", required = false) Short countryId

) { }
