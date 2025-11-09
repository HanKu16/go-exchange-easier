package com.go_exchange_easier.backend.controller;

import com.go_exchange_easier.backend.annoations.docs.search.GetUniversitiesApiDocs;
import com.go_exchange_easier.backend.annoations.docs.search.GetUsersByExchangeApiDocs;
import com.go_exchange_easier.backend.annoations.docs.search.GetUsersByNickApiDocs;
import com.go_exchange_easier.backend.dto.search.GetUniversityResponse;
import com.go_exchange_easier.backend.dto.search.GetUserByNickResponse;
import com.go_exchange_easier.backend.dto.search.GetUserByExchangeResponse;
import com.go_exchange_easier.backend.service.SearchService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
@Tag(name = "Search", description = "Operations related to searching.")
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/users/exchanges")
    @GetUsersByExchangeApiDocs
    public ResponseEntity<Page<GetUserByExchangeResponse>> getUsersByExchange(
            @RequestParam(value = "universityId", required = false) Short universityId,
            @RequestParam(value = "cityId", required = false) Integer cityId,
            @RequestParam(value = "countryId", required = false) Short countryId,
            @RequestParam(value = "majorId", required = false) Short majorId,
            @RequestParam(value = "startDate", required = false) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) LocalDate endDate,
            @ParameterObject Pageable pageable) {
        return ResponseEntity.ok(searchService.findUsersByExchange(
                universityId, cityId, countryId, majorId, startDate,
                endDate, pageable));
    }

    @GetMapping("/users/nick")
    @GetUsersByNickApiDocs
    public ResponseEntity<Page<GetUserByNickResponse>> getUsersByNick(
            @RequestParam(value = "nick") String nick,
            @ParameterObject Pageable pageable) {
        Page<GetUserByNickResponse> response = searchService
                .findUsersByNick(nick, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/universities")
    @GetUniversitiesApiDocs
    public ResponseEntity<Page<GetUniversityResponse>> getUniversities(
            @RequestParam(value = "nativeName", required = false) String nativeName,
            @RequestParam(value = "englishName", required = false) String englishName,
            @RequestParam(value = "cityId", required = false) Integer cityId,
            @RequestParam(value = "countryId", required = false) Short countryId,
            @ParameterObject Pageable pageable) {
        Page<GetUniversityResponse> response = searchService.findUniversities(
                nativeName, englishName, cityId, countryId, pageable);
        return ResponseEntity.ok(response);
    }

}
