package com.go_exchange_easier.backend.core.domain.location.city.impl;

import com.go_exchange_easier.backend.core.domain.location.city.*;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CitiesServiceImpl implements CitiesService {

    private final CitiesRepository citiesRepository;

    @Override
    @Cacheable(value="cities", key="'country:' + #countryId",
            condition="#countryId != null")
    public List<CityDetails> getAll(Short countryId) {
        Specification<City> specification = (root, query, cb) -> null;
        if (countryId != null) {
            specification = specification.and(CitySpecification
                    .hasCountryId(countryId));
        }
        List<City> cities = citiesRepository.findAll(specification);
        return cities.stream().map(CityDetails::fromEntity).toList();
    }

}
