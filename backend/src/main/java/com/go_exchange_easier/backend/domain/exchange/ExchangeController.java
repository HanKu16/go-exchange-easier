package com.go_exchange_easier.backend.domain.exchange;

import com.go_exchange_easier.backend.domain.exchange.annotations.CreateExchangeApiDocs;
import com.go_exchange_easier.backend.domain.exchange.annotations.DeleteExchangeApiDocs;
import com.go_exchange_easier.backend.domain.exchange.annotations.GetPageApiDocs;
import com.go_exchange_easier.backend.domain.exchange.dto.ExchangeDetails;
import com.go_exchange_easier.backend.domain.exchange.dto.CreateExchangeRequest;
import com.go_exchange_easier.backend.domain.exchange.dto.CreateExchangeResponse;
import com.go_exchange_easier.backend.domain.auth.UserCredentials;
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
public class ExchangeController {

    private final ExchangeService exchangeService;

    @GetMapping
    @GetPageApiDocs
    public ResponseEntity<Page<ExchangeDetails>> getPage(
            @ParameterObject @ModelAttribute ExchangeFilters filters,
            @ParameterObject Pageable pageable) {
        Page<ExchangeDetails> page = exchangeService.getPage(filters, pageable);
        return ResponseEntity.ok()
                .body(page);
    }

    @PostMapping
    @CreateExchangeApiDocs
    public ResponseEntity<CreateExchangeResponse> create(
            @RequestBody @Valid CreateExchangeRequest request,
            @AuthenticationPrincipal UserCredentials principal) {
        CreateExchangeResponse response = exchangeService
                .create(principal.getUser().getId(), request);
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
