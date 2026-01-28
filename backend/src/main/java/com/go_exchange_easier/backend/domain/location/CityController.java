package com.go_exchange_easier.backend.domain.location;

import com.go_exchange_easier.backend.domain.location.annotations.city.GetAllApiDocs;
import com.go_exchange_easier.backend.common.dto.Listing;
import com.go_exchange_easier.backend.domain.location.dto.CityDetails;
import com.go_exchange_easier.backend.domain.university.UniversityService;
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
