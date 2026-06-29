package com.go_exchange_easier.backend.core.domain.report;

public enum ReportStatus {
    NEW,
    IN_PROGRESS,
    RESOLVED,
    REJECTED;

    public boolean canTransitTo(ReportStatus newStatus) {
        return switch (this) {
            case NEW -> newStatus == IN_PROGRESS;
            case IN_PROGRESS -> newStatus == RESOLVED || newStatus == REJECTED;
            case RESOLVED, REJECTED -> false;
        };
    }
}