package com.go_exchange_easier.backend.core.domain.university.impl;

import com.go_exchange_easier.backend.core.domain.location.city.impl.CityMapper;
import com.go_exchange_easier.backend.core.domain.university.University;
import com.go_exchange_easier.backend.core.domain.university.dto.UniversityDetails;
import com.go_exchange_easier.backend.core.domain.university.dto.UniversitySummary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniversityMapper {

    private final CityMapper cityMapper;

    public UniversityDetails toDetails(University university) {
        if (university == null) {
            return null;
        }
        return new UniversityDetails(
                university.getId(),
                university.getOriginalName(),
                university.getEnglishName(),
                cityMapper.toDetails(university.getCity())
        );
    }

    public UniversitySummary toSummary(University university) {
        if (university == null) {
            return null;
        }
        return new UniversitySummary(
                university.getId(),
                university.getOriginalName(),
                university.getEnglishName()
        );
    }

}
