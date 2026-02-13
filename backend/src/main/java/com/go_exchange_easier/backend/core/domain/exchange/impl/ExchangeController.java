package com.go_exchange_easier.backend.core.domain.exchange.impl;

import com.go_exchange_easier.backend.core.domain.auth.dto.AuthenticatedUser;
import com.go_exchange_easier.backend.core.domain.exchange.ExchangeApi;
import com.go_exchange_easier.backend.core.domain.exchange.ExchangeService;
import com.go_exchange_easier.backend.core.domain.exchange.dto.ExchangeDetails;
import com.go_exchange_easier.backend.core.domain.exchange.dto.CreateExchangeRequest;
import com.go_exchange_easier.backend.core.domain.exchange.dto.ExchangeFilters;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

@RestController
@RequiredArgsConstructor
public class ExchangeController implements ExchangeApi {

    private final ExchangeService exchangeService;

    @Override
    public ResponseEntity<Page<ExchangeDetails>> getPage(
            ExchangeFilters filters, Pageable pageable) {
        Page<ExchangeDetails> page = exchangeService.getPage(filters, pageable);
        return ResponseEntity.ok()
                .body(page);
    }

    @Override
    public ResponseEntity<ExchangeDetails> create(
            CreateExchangeRequest request,
            AuthenticatedUser authenticatedUser) {
        ExchangeDetails response = exchangeService
                .create(authenticatedUser.getId(), request);
        return ResponseEntity.created(URI.create(
                "/api/exchange/" + response.id()))
                .body(response);
    }

    @Override
    public ResponseEntity<Void> delete(Integer exchangeId,
            AuthenticatedUser authenticatedUser) {
        exchangeService.delete(exchangeId, authenticatedUser.getId());
        return ResponseEntity.noContent().build();
    }

}
