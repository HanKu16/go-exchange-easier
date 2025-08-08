package com.go_exchange_easier.backend.controller;

import com.go_exchange_easier.backend.dto.exchange.CreateExchangeRequest;
import com.go_exchange_easier.backend.dto.exchange.CreateExchangeResponse;
import com.go_exchange_easier.backend.service.ExchangeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/exchange")
@RequiredArgsConstructor
@EnableMethodSecurity
public class ExchangeController {

    private final ExchangeService exchangeService;

    @PostMapping
    @PreAuthorize("#request.userId == authentication.principal.id")
    public ResponseEntity<CreateExchangeResponse> create(
            @RequestBody @Valid CreateExchangeRequest request) {
        CreateExchangeResponse response = exchangeService.create(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{exchangeId}")
    public ResponseEntity<Void> delete(@PathVariable Integer exchangeId) {
        exchangeService.delete(exchangeId);
        return ResponseEntity.noContent().build();
    }

}
