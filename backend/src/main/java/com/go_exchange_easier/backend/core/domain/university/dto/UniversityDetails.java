package com.go_exchange_easier.backend.core.domain.university.dto;

import com.go_exchange_easier.backend.core.domain.location.city.CityDetails;

public record UniversityDetails(

        Short id,
        String nativeName,
        String englishName,
        CityDetails city

) { }
