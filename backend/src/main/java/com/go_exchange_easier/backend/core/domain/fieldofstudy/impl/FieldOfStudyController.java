package com.go_exchange_easier.backend.core.domain.fieldofstudy.impl;

import com.go_exchange_easier.backend.common.dto.Listing;
import com.go_exchange_easier.backend.core.domain.fieldofstudy.FieldOfStudyApi;
import com.go_exchange_easier.backend.core.domain.fieldofstudy.FieldOfStudyService;
import com.go_exchange_easier.backend.core.domain.fieldofstudy.FieldOfStudySummary;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor
public class FieldOfStudyController implements FieldOfStudyApi {

    private final FieldOfStudyService fieldOfStudyService;

    @Override
    public ResponseEntity<Listing<FieldOfStudySummary>> getAll() {
        List<FieldOfStudySummary> fieldsOfStudy = fieldOfStudyService.getAll();
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(1, TimeUnit.DAYS))
                .body(Listing.of(fieldsOfStudy));
    }

}
