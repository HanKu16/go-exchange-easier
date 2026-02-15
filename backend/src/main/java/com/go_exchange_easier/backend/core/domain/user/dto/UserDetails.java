package com.go_exchange_easier.backend.core.domain.user.dto;

import com.go_exchange_easier.backend.core.domain.location.country.CountrySummary;
import com.go_exchange_easier.backend.core.domain.university.dto.UniversitySummary;

public record UserDetails(

        Integer id,
        String nick,
        CountrySummary country,
        UniversitySummary university

) { }
