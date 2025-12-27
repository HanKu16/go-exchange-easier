package com.go_exchange_easier.backend.service;

import com.go_exchange_easier.backend.dto.details.ExchangeDetails;
import com.go_exchange_easier.backend.dto.exchange.CreateExchangeRequest;
import com.go_exchange_easier.backend.dto.exchange.CreateExchangeResponse;
import com.go_exchange_easier.backend.dto.filter.ExchangeFilters;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExchangeService {

    Page<ExchangeDetails> getPage(ExchangeFilters filters, Pageable pageable);
    CreateExchangeResponse create(int userId, CreateExchangeRequest request);
    void delete(int exchangeId);

}
