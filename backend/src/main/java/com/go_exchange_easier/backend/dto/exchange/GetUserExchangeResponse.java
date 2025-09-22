package com.go_exchange_easier.backend.dto.exchange;

import java.time.LocalDate;

public record GetUserExchangeResponse(

        Integer id,
        TimeRangeDto timeRange,
        UniversityDto university,
        UniversityMajorDto universityMajor,
        CityDto city

) {

    public record TimeRangeDto(

            LocalDate startedAt,
            LocalDate endAt

    ) {}

    public record UniversityDto(

            Short id,
            String nativeName,
            String englishName

    ) {}

    public record UniversityMajorDto(

            Short id,
            String name

    ) {}

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
