package com.go_exchange_easier.backend.controller;

import com.go_exchange_easier.backend.annoations.docs.city.GetAllApiDocs;
import com.go_exchange_easier.backend.dto.city.GetCityResponse;
import com.go_exchange_easier.backend.service.CitiesService;
import com.go_exchange_easier.backend.service.UniversityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/cities")
@RequiredArgsConstructor
public class CityController {

    private final UniversityService universityService;
    private final CitiesService citiesService;

    @GetMapping
    @GetAllApiDocs
    public ResponseEntity<List<GetCityResponse>> getAll(
            @RequestParam(value = "countryId", required = false) Short countryId
    ) {
        List<GetCityResponse> response = citiesService.getAll(countryId);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(60, TimeUnit.MINUTES))
                .body(response);
    }

}
