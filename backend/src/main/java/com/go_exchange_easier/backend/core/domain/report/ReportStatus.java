package com.go_exchange_easier.backend.core.domain.report;

import com.go_exchange_easier.backend.common.dto.DictionaryEnum;

public enum ReportStatus implements DictionaryEnum {
    NEW("New"),
    IN_PROGRESS("In progress"),
    RESOLVED("Resolved"),
    REJECTED("Rejected");

    private final String label;

    ReportStatus(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }

    public boolean canTransitTo(ReportStatus newStatus) {
        return switch (this) {
            case NEW -> newStatus == IN_PROGRESS;
            case IN_PROGRESS -> newStatus == RESOLVED || newStatus == REJECTED;
            case RESOLVED, REJECTED -> false;
        };
    }
}