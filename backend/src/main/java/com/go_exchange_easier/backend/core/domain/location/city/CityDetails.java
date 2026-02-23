package com.go_exchange_easier.backend.core.domain.location.city;

import com.go_exchange_easier.backend.core.domain.location.country.dto.CountryDetails;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

@Schema(requiredProperties = {"id", "name", "country"})
public record CityDetails(

        Integer id,
        String name,
        CountryDetails country

) implements Serializable { }
