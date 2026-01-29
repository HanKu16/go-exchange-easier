package com.go_exchange_easier.backend.domain.location.country.impl;

import com.go_exchange_easier.backend.domain.location.country.Country;
import com.go_exchange_easier.backend.domain.location.country.CountryDetails;
import com.go_exchange_easier.backend.domain.location.country.CountryRepository;
import com.go_exchange_easier.backend.domain.location.country.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value="countries", key="'all'")
    public List<CountryDetails> getAll() {
        List<Country> countries = countryRepository.findAll();
        return countries.stream()
                .map(CountryDetails::fromEntity)
                .toList();
    }

}
