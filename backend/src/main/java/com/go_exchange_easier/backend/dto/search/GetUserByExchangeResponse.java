package com.go_exchange_easier.backend.dto.search;

import java.time.LocalDate;

public record GetUserByExchangeResponse(

        Integer id,
        String nick,
        ExchangeDto exchange

) {

    public record ExchangeDto(

            Integer id,
            TimeRangeDto timeRange,
            UniversityDto university,
            UniversityMajorDto universityMajor

    ) {}

    public record TimeRangeDto(

            LocalDate startedAt,
            LocalDate endAt

    ) {}

    public record UniversityDto(

            Short id,
            String nativeName,
            String englishName,
            CityDto city

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
