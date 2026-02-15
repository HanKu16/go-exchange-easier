package com.go_exchange_easier.backend.core.domain.location.city;

import com.go_exchange_easier.backend.core.domain.location.country.CountryDetails;
import java.io.Serializable;

public record CityDetails(

        Integer id,
        String name,
        CountryDetails country

) implements Serializable { }
