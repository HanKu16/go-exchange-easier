package com.go_exchange_easier.backend.dto.details;

import com.go_exchange_easier.backend.model.University;

public record UniversityDetails(

        Short id,
        String nativeName,
        String englishName,
        CityDetails city

) {

    public static UniversityDetails fromEntity(University u) {
        return new UniversityDetails(
                u.getId(),
                u.getOriginalName(),
                u.getEnglishName(),
                u.getCity() != null ?
                        CityDetails.fromEntity(u.getCity()) :
                        null
        );
    }

}
