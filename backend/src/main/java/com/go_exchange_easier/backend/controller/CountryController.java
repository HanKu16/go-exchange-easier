package com.go_exchange_easier.backend.controller;

import com.go_exchange_easier.backend.annoations.docs.country.GetCountiesApiDocs;
import com.go_exchange_easier.backend.dto.country.GetCountryResponse;
import com.go_exchange_easier.backend.service.CountryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/countries")
@RequiredArgsConstructor
@Tag(name = "Country", description = "Operations related to countries.")
public class CountryController {

    private final CountryService countryService;

    @GetMapping
    @GetCountiesApiDocs
    public ResponseEntity<List<GetCountryResponse>> getAll() {
        List<GetCountryResponse> response = countryService.getAll();
        return ResponseEntity.ok(response);
    }

}
