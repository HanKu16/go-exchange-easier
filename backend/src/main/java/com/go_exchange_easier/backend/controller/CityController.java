package com.go_exchange_easier.backend.controller;

import com.go_exchange_easier.backend.annoations.docs.city.GetAllApiDocs;
import com.go_exchange_easier.backend.dto.common.Listing;
import com.go_exchange_easier.backend.dto.details.CityDetails;
import com.go_exchange_easier.backend.service.CitiesService;
import com.go_exchange_easier.backend.service.UniversityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/cities")
@RequiredArgsConstructor
@Tag(name = "City", description = "Operations related to cities.")
public class CityController {

    private final UniversityService universityService;
    private final CitiesService citiesService;

    @GetMapping
    @GetAllApiDocs
    public ResponseEntity<Listing<CityDetails>> getAll(
            @RequestParam(value = "countryId", required = false) Short countryId
    ) {
        List<CityDetails> cities = citiesService.getAll(countryId);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(1, TimeUnit.DAYS))
                .body(Listing.of(cities));
    }

}
