package com.go_exchange_easier.backend.dto.university;

public record GetUniversityResponse(

    Short id,
    String nativeName,
    String englishName,
    CityDto city

) {

    public record CityDto(

            Integer id,
            String name,
            CountryDto country

    ) {}

    public record CountryDto(

            Short id,
            String name

    ) {}

}

