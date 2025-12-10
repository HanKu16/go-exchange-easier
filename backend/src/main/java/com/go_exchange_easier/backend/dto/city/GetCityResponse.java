package com.go_exchange_easier.backend.dto.city;

public record GetCityResponse(

        Integer id,
        String name,
        CountryDto country

) {

    public record CountryDto(

            Short id,
            String name)

    { }

}
