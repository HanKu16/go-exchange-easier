package com.go_exchange_easier.backend.domain.fieldofstudy.dto;

import com.go_exchange_easier.backend.domain.fieldofstudy.UniversityMajor;

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
