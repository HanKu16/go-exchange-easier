package com.go_exchange_easier.backend.service;

import com.go_exchange_easier.backend.dto.exchange.CreateExchangeRequest;
import com.go_exchange_easier.backend.dto.exchange.CreateExchangeResponse;

public interface ExchangeService {

    CreateExchangeResponse create(int userId, CreateExchangeRequest request);
    void delete(int exchangeId);

}
