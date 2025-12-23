package com.go_exchange_easier.backend.service.impl;

import com.go_exchange_easier.backend.dto.details.CountryDetails;
import com.go_exchange_easier.backend.model.Country;
import com.go_exchange_easier.backend.repository.CountryRepository;
import com.go_exchange_easier.backend.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    @Override
    public List<CountryDetails> getAll() {
        List<Country> countries = countryRepository.findAll();
        return countries.stream()
                .map(CountryDetails::fromEntity)
                .toList();
    }

}
