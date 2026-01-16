package com.go_exchange_easier.backend.dto.details;

import com.go_exchange_easier.backend.model.Country;

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
