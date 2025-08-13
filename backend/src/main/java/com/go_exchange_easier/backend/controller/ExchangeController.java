package com.go_exchange_easier.backend.controller;

import com.go_exchange_easier.backend.annoations.docs.exchange.CreateExchangeApiDocs;
import com.go_exchange_easier.backend.annoations.docs.exchange.DeleteExchangeApiDocs;
import com.go_exchange_easier.backend.dto.exchange.CreateExchangeRequest;
import com.go_exchange_easier.backend.dto.exchange.CreateExchangeResponse;
import com.go_exchange_easier.backend.model.UserCredentials;
import com.go_exchange_easier.backend.service.ExchangeService;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Exchange", description = "Operations related to exchanges.")
public class ExchangeController {

    private final ExchangeService exchangeService;

    @PostMapping
    @CreateExchangeApiDocs
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
    @DeleteExchangeApiDocs
    public ResponseEntity<Void> delete(@PathVariable Integer exchangeId) {
        exchangeService.delete(exchangeId);
        return ResponseEntity.noContent().build();
    }

}
