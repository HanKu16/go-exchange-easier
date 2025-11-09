package com.go_exchange_easier.backend.dto.search;

public record GetUserByNickResponse(

        Integer id,
        String nick,
        CountryDto countryOfOrigin,
        UniversityDto homeUniversity

) {

    public record UniversityDto(

            Short id,
            String nativeName,
            String englishName

    ) {}


    public record CountryDto(

            Short id,
            String name

    ) {}

}
