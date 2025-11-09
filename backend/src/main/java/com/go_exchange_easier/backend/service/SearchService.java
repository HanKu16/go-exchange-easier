package com.go_exchange_easier.backend.service;

import com.go_exchange_easier.backend.dto.search.GetUniversityResponse;
import com.go_exchange_easier.backend.dto.search.GetUserByNickResponse;
import com.go_exchange_easier.backend.dto.search.GetUserByExchangeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;

public interface SearchService {

    Page<GetUserByExchangeResponse> findUsersByExchange(
            Short universityId, Integer cityId, Short countryId,
            Short majorId, LocalDate startDate, LocalDate endDate,
            Pageable pageable);
    Page<GetUserByNickResponse> findUsersByNick(
            String nick, Pageable pageable);
    Page<GetUniversityResponse> findUniversities(
            String nativeName, String englishName,
            Integer cityId, Short countryId, Pageable pageable);

}
