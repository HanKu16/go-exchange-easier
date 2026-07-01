package com.go_exchange_easier.backend.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(requiredProperties = {"value", "label"})
public record DictionaryEntry(
        String value,
        String label
) { }