package com.go_exchange_easier.backend.controller;

import com.go_exchange_easier.backend.dto.common.Listing;
import com.go_exchange_easier.backend.dto.summary.UniversityMajorSummary;
import com.go_exchange_easier.backend.service.UniversityMajorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/universityMajors")
@RequiredArgsConstructor
@Tag(name = "University Major", description =
        "Operations related to university majors.")
public class UniversityMajorController {

    private final UniversityMajorService universityMajorService;

    @GetMapping
    public ResponseEntity<Listing<UniversityMajorSummary>> getAll() {
        List<UniversityMajorSummary> majors = universityMajorService.getAll();
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(1, TimeUnit.DAYS))
                .body(Listing.of(majors));
    }

}
