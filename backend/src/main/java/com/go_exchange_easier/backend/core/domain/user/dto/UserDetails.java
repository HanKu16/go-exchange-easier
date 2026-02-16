package com.go_exchange_easier.backend.core.domain.user.dto;

import com.go_exchange_easier.backend.core.domain.location.country.dto.CountryDetails;
import com.go_exchange_easier.backend.core.domain.university.dto.UniversityDetails;
import java.io.Serializable;

public record UserDetails(

        Integer id,
        String nick,
        CountryDetails country,
        UniversityDetails university

) implements Serializable { }
