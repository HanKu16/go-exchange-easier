package com.go_exchange_easier.backend.service;

import com.go_exchange_easier.backend.dto.exchange.CreateExchangeRequest;
import com.go_exchange_easier.backend.dto.exchange.CreateExchangeResponse;
import com.go_exchange_easier.backend.dto.exchange.GetUserExchangeResponse;
import java.util.List;

public interface ExchangeService {

    List<GetUserExchangeResponse> getByUserId(int userId);
    CreateExchangeResponse create(int userId, CreateExchangeRequest request);
    void delete(int exchangeId);

}
