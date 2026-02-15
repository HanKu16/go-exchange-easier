package com.go_exchange_easier.backend.core.domain.location.city.impl;

import com.go_exchange_easier.backend.core.domain.location.city.City;
import com.go_exchange_easier.backend.core.domain.location.city.CityDetails;
import com.go_exchange_easier.backend.core.domain.location.country.impl.CountryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CityMapper {

    private final CountryMapper countryMapper;

    public CityDetails toDetails(City city) {
        if (city == null) {
            return null;
        }
        return new CityDetails(
                city.getId(),
                city.getEnglishName(),
                countryMapper.toDetails(city.getCountry()));
    }

}
