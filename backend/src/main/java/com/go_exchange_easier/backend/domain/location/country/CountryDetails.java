package com.go_exchange_easier.backend.domain.location.country;

import java.io.Serializable;

public record CountryDetails(

        Short id,
        String englishName

) implements Serializable {

    public static CountryDetails fromEntity(Country c) {
        return new CountryDetails(
                c.getId(),
                c.getEnglishName()
        );
    }

}
