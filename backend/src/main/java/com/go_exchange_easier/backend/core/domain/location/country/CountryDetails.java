package com.go_exchange_easier.backend.core.domain.location.country;

import java.io.Serializable;

public record CountryDetails(

        Short id,
        String englishName,
        String flagUrl

) implements Serializable { }