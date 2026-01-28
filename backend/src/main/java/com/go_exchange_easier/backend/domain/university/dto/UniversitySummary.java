package com.go_exchange_easier.backend.domain.university.dto;

import com.go_exchange_easier.backend.domain.university.University;

public record UniversitySummary(

        Short id,
        String nativeName,
        String englishName

) {

    public static UniversitySummary fromEntity(University u) {
        return new UniversitySummary(
                u.getId(),
                u.getOriginalName(),
                u.getEnglishName()
        );
    }

}
