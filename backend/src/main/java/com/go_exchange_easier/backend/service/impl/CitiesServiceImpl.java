package com.go_exchange_easier.backend.service.impl;

import com.go_exchange_easier.backend.dto.details.CityDetails;
import com.go_exchange_easier.backend.model.City;
import com.go_exchange_easier.backend.repository.CitiesRepository;
import com.go_exchange_easier.backend.repository.specification.CitySpecification;
import com.go_exchange_easier.backend.service.CitiesService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
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
