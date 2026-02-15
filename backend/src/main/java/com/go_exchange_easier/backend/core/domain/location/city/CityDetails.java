package com.go_exchange_easier.backend.core.domain.location.city;

import com.go_exchange_easier.backend.core.domain.location.country.CountrySummary;

import java.io.Serializable;

public record CityDetails(

        Integer id,
        String name,
        CountrySummary country

) implements Serializable {

    public static CityDetails fromEntity(City c) {
        return new CityDetails(
                c.getId(),
                c.getEnglishName(),
                c.getCountry() != null ?
                        CountrySummary.fromEntity(c.getCountry()) :
                        null
        );
    }

}
