package com.go_exchange_easier.backend.core.domain.university.dto;

public record UniversityProfile(

    Short id,
    String nativeName,
    String englishName,
    String linkToWebsite,
    String cityName,
    String countryName,
    Boolean isFollowed

) { }
