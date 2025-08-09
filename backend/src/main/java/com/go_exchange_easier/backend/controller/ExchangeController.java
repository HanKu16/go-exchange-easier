package com.go_exchange_easier.backend.controller;

import com.go_exchange_easier.backend.dto.exchange.CreateExchangeRequest;
import com.go_exchange_easier.backend.dto.exchange.CreateExchangeResponse;
import com.go_exchange_easier.backend.model.UserCredentials;
import com.go_exchange_easier.backend.service.ExchangeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

@RestController
@RequestMapping("/api/exchange")
@RequiredArgsConstructor
@EnableMethodSecurity
public class ExchangeController {

    private final ExchangeService exchangeService;

    @PostMapping
    public ResponseEntity<CreateExchangeResponse> create(
            @RequestBody @Valid CreateExchangeRequest request,
            @AuthenticationPrincipal UserCredentials principal) {
        CreateExchangeResponse response = exchangeService
                .create(principal.getId(), request);
        return ResponseEntity.created(URI.create(
                "/api/exchange/" + response.id()))
                .body(response);
    }

    @DeleteMapping("/{exchangeId}")
    public ResponseEntity<Void> delete(@PathVariable Integer exchangeId) {
        exchangeService.delete(exchangeId);
        return ResponseEntity.noContent().build();
    }

}
