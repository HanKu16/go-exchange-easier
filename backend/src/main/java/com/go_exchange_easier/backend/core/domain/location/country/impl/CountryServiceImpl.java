package com.go_exchange_easier.backend.core.domain.location.country.impl;

import com.go_exchange_easier.backend.common.storage.FileStorageService;
import com.go_exchange_easier.backend.core.domain.location.country.*;
import com.go_exchange_easier.backend.core.infrastracture.storage.BucketProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CountryServiceImpl implements CountryService {

    private final FileStorageService fileStorageService;
    private final CountryRepository countryRepository;
    private final BucketProperties bucketProperties;

    @Override
    @Cacheable(value="countries", key="'all'")
    public List<CountryDetails> getAll() {
        List<Country> countries = countryRepository.findAll();
        return countries.stream()
                .map(c -> new CountryDetails(c.getId(), c.getEnglishName(),
                        fileStorageService.getPublicUrl(
                                bucketProperties.getFlag(), c.getFlagKey())))
                .toList();
    }

}
