package com.go_exchange_easier.backend.core.domain.university.dto;

import java.io.Serializable;

public record UniversitySummary(

        Short id,
        String nativeName,
        String englishName

) implements Serializable { }
