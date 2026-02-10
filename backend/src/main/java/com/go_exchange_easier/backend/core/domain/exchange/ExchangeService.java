package com.go_exchange_easier.backend.core.domain.exchange;

import com.go_exchange_easier.backend.core.domain.exchange.dto.ExchangeDetails;
import com.go_exchange_easier.backend.core.domain.exchange.dto.CreateExchangeRequest;
import com.go_exchange_easier.backend.core.domain.exchange.dto.ExchangeFilters;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExchangeService {

    Page<ExchangeDetails> getPage(ExchangeFilters filters, Pageable pageable);
    ExchangeDetails create(int userId, CreateExchangeRequest request);
    void delete(int exchangeId, int userId);

}
