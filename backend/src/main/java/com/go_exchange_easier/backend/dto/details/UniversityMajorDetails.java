package com.go_exchange_easier.backend.dto.details;

import com.go_exchange_easier.backend.model.UniversityMajor;

public record UniversityMajorDetails(

        Short id,
        String name

) {

    public static UniversityMajorDetails fromEntity(UniversityMajor u) {
        return new UniversityMajorDetails(
                u.getId(),
                u.getName()
        );
    }

}
