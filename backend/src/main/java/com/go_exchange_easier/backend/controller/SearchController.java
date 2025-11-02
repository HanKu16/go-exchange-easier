package com.go_exchange_easier.backend.controller;

import com.go_exchange_easier.backend.annoations.docs.search.GetUsersByExchangeApiDocs;
import com.go_exchange_easier.backend.dto.search.GetUsersByExchangeResponse;
import com.go_exchange_easier.backend.service.SearchService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
@Tag(name = "Search", description = "Operations related to searching.")
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/users/exchanges")
    @GetUsersByExchangeApiDocs
    public ResponseEntity<Page<GetUsersByExchangeResponse>> getUsersByExchange(
            @RequestParam(value = "universityId", required = false) Short universityId,
            @RequestParam(value = "cityId", required = false) Integer cityId,
            @RequestParam(value = "countryId", required = false) Short countryId,
            @RequestParam(value = "majorId", required = false) Short majorId,
            @ParameterObject Pageable pageable) {
        return ResponseEntity.ok(searchService.findUsersByExchange(
                universityId, cityId, countryId, majorId, pageable));
    }

}
