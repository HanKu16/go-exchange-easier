package com.go_exchange_easier.backend.domain.location.city.impl;

import com.go_exchange_easier.backend.common.dto.Listing;
import com.go_exchange_easier.backend.domain.location.city.CitiesService;
import com.go_exchange_easier.backend.domain.location.city.CityApi;
import com.go_exchange_easier.backend.domain.location.city.CityDetails;
import com.go_exchange_easier.backend.domain.university.UniversityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor
public class CityController implements CityApi {

    private final UniversityService universityService;
    private final CitiesService citiesService;

    @Override
    public ResponseEntity<Listing<CityDetails>> getAll(
            @RequestParam(value = "countryId", required = false) Short countryId) {
        List<CityDetails> cities = citiesService.getAll(countryId);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(1, TimeUnit.DAYS))
                .body(Listing.of(cities));
    }

}
