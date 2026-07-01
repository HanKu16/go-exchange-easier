package com.go_exchange_easier.backend.core.domain.report.impl;

import com.go_exchange_easier.backend.common.dto.DictionaryEnum;
import com.go_exchange_easier.backend.core.domain.report.*;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReportController implements ReportApi {

    @Override
    public ResponseEntity<ReportDictionary> getDictionary() {
        ReportDictionary dictionary = new ReportDictionary(
                Arrays.stream(ReportReason.values()).map(DictionaryEnum::toEntry).toList(),
                Arrays.stream(ReportStatus.values()).map(DictionaryEnum::toEntry).toList(),
                Arrays.stream(ReportType.values()).map(DictionaryEnum::toEntry).toList()
        );
        return ResponseEntity.ok(dictionary);
    }

}