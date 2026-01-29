package com.go_exchange_easier.backend.domain.exchange.impl;

import com.go_exchange_easier.backend.domain.exchange.ExchangeApi;
import com.go_exchange_easier.backend.domain.exchange.ExchangeService;
import com.go_exchange_easier.backend.domain.exchange.dto.ExchangeDetails;
import com.go_exchange_easier.backend.domain.exchange.dto.CreateExchangeRequest;
import com.go_exchange_easier.backend.domain.auth.entity.UserCredentials;
import com.go_exchange_easier.backend.domain.exchange.dto.ExchangeFilters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

@RestController
@RequestMapping("/api/exchanges")
@RequiredArgsConstructor
@EnableMethodSecurity
@Tag(name = "Exchange", description = "Operations related to exchanges.")
public class ExchangeController implements ExchangeApi {

    private final ExchangeService exchangeService;

    @Override
    public ResponseEntity<Page<ExchangeDetails>> getPage(
            @ParameterObject @ModelAttribute ExchangeFilters filters,
            @ParameterObject Pageable pageable) {
        Page<ExchangeDetails> page = exchangeService.getPage(filters, pageable);
        return ResponseEntity.ok()
                .body(page);
    }

    @Override
    public ResponseEntity<ExchangeDetails> create(
            @RequestBody @Valid CreateExchangeRequest request,
            @AuthenticationPrincipal UserCredentials principal) {
        ExchangeDetails response = exchangeService
                .create(principal.getUser().getId(), request);
        return ResponseEntity.created(URI.create(
                "/api/exchange/" + response.id()))
                .body(response);
    }

    @Override
    public ResponseEntity<Void> delete(
            @PathVariable Integer exchangeId,
            @AuthenticationPrincipal UserCredentials principal) {
        exchangeService.delete(exchangeId, principal.getUser().getId());
        return ResponseEntity.noContent().build();
    }

}
