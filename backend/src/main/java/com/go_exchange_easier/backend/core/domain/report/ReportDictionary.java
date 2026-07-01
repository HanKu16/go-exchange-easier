package com.go_exchange_easier.backend.core.domain.report;

import com.go_exchange_easier.backend.common.dto.DictionaryEntry;
import java.util.List;

public record ReportDictionary(
        List<DictionaryEntry> reasons,
        List<DictionaryEntry> statuses,
        List<DictionaryEntry> types
) { }