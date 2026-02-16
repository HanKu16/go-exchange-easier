package com.go_exchange_easier.backend.core.domain.location.country.dto;

import java.io.Serializable;

public record CountrySummary(

        Short id,
        String englishName

) implements Serializable { }
