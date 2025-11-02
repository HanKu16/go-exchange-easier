package com.go_exchange_easier.backend.service;

import com.go_exchange_easier.backend.dto.search.GetUsersByExchangeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchService {

    Page<GetUsersByExchangeResponse> findUsersByExchange(
            Short universityId, Integer cityId, Short countryId,
            Short majorId, Pageable pageable);

}
