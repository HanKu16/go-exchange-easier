package com.go_exchange_easier.backend.core.domain.location.country.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

@Schema(requiredProperties = {"id", "englishName"})
public record CountrySummary(

        Short id,
        String englishName

) implements Serializable { }
