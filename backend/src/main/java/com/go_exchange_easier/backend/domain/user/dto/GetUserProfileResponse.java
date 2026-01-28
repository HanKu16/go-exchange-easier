package com.go_exchange_easier.backend.domain.user.dto;

public record GetUserProfileResponse(

        Integer userId,
        String nick,
        String avatarUrl,
        String description,
        Boolean isFollowed,
        UniversityDto homeUniversity,
        CountryDto countryOfOrigin,
        StatusDto status

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

    public record StatusDto(

            Short id,
            String name

    ) {}

}
