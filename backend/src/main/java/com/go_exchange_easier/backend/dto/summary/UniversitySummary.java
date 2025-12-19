package com.go_exchange_easier.backend.dto.summary;

import com.go_exchange_easier.backend.model.University;

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
