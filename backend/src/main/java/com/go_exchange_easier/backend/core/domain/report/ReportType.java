package com.go_exchange_easier.backend.core.domain.report;

import com.go_exchange_easier.backend.common.dto.DictionaryEnum;

public enum ReportType implements DictionaryEnum {
    USER("User"),
    UNIVERSITY_REVIEW("University review"),
    CHAT("Chat");

    private final String label;

    ReportType(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }
}