package com.go_exchange_easier.backend.core.domain.report;

import com.go_exchange_easier.backend.common.dto.DictionaryEnum;

public enum ReportReason implements DictionaryEnum {
    HARASSMENT("Harassment"),
    HATE_SPEECH("Hate speech"),
    THREATS("Threats"),
    INAPPROPRIATE_CONTENT("Inappropriate content"),
    SPAM("Spam"),
    SCAM("Scam"),
    MISINFORMATION("Misinformation"),
    OTHER("Other");

    private final String label;

    ReportReason(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }
}