package com.go_exchange_easier.backend.domain.location.dto;

import com.go_exchange_easier.backend.domain.location.City;

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
