package com.go_exchange_easier.backend.core.domain.location.country.impl;

import com.go_exchange_easier.backend.core.domain.location.country.Country;
import com.go_exchange_easier.backend.core.domain.location.country.CountryDetails;
import com.go_exchange_easier.backend.core.domain.location.country.CountryService;
import com.go_exchange_easier.backend.core.domain.location.country.CountrySummary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CountryMapper {

    private final CountryService countryService;

    public CountryDetails toDetails(Country country) {
        if (country == null) {
            return null;
        }
        return new CountryDetails(
                country.getId(),
                country.getEnglishName(),
                country.getFlagKey() != null ?
                    countryService.getFlagUrl(country.getFlagKey()) :
                        null
        );
    }

    public CountrySummary toSummary(Country country) {
        return new CountrySummary(
                country.getId(),
                country.getEnglishName()
        );
    }

}