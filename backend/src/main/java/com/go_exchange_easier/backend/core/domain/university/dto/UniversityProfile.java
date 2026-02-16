package com.go_exchange_easier.backend.core.domain.university.dto;

import com.go_exchange_easier.backend.core.domain.location.city.CityDetails;
import java.io.Serializable;

public record UniversityProfile(

    Short id,
    String nativeName,
    String englishName,
    String linkToWebsite,
    CityDetails city,
    Boolean isFollowed

)  implements Serializable { }
