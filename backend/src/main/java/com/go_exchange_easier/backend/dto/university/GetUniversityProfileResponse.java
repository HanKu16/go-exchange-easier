package com.go_exchange_easier.backend.dto.university;

public record GetUniversityProfileResponse(

    Short id,
    String nativeName,
    String englishName,
    String linkToWebsite,
    String cityName,
    String countryName,
    Boolean isFollowed

) { }
