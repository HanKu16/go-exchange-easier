package com.go_exchange_easier.backend.domain.location.country.impl;

import com.go_exchange_easier.backend.domain.location.annotations.country.GetAllApiDocs;
import com.go_exchange_easier.backend.common.dto.Listing;
import com.go_exchange_easier.backend.domain.location.country.CountryApi;
import com.go_exchange_easier.backend.domain.location.country.CountryDetails;
import com.go_exchange_easier.backend.domain.location.country.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor
public class CountryController implements CountryApi {

    private final CountryService countryService;

    @GetMapping
    @GetAllApiDocs
    @Override
    public ResponseEntity<Listing<CountryDetails>> getAll() {
        List<CountryDetails> countries = countryService.getAll();
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(7, TimeUnit.DAYS))
                .body(Listing.of(countries));
    }

}
