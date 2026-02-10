package com.go_exchange_easier.backend.core.domain.location.city;

import com.go_exchange_easier.backend.core.domain.location.country.CountryDetails;

import java.io.Serializable;

public record CityDetails(

        Integer id,
        String name,
        CountryDetails country

) implements Serializable {

    public static CityDetails fromEntity(City c) {
        return new CityDetails(
                c.getId(),
                c.getEnglishName(),
                c.getCountry() != null ?
                        CountryDetails.fromEntity(c.getCountry()) :
                        null
        );
    }

}
