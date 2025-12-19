package com.go_exchange_easier.backend.dto.details;

import com.go_exchange_easier.backend.model.City;

public record CityDetails(

        Integer id,
        String name,
        CountryDetails country

) {

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
