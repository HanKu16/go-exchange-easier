package com.go_exchange_easier.backend.dto.university;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request body for deleting university review reaction")
public record GetUniversityResponse(

    Short id,
    String nativeName,
    String englishName,
    CityDto city

) {

    public record CityDto(

            Integer id,
            String name)

    { }

}

