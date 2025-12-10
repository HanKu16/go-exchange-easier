package com.go_exchange_easier.backend.service.impl;

import com.go_exchange_easier.backend.dto.city.GetCityResponse;
import com.go_exchange_easier.backend.model.City;
import com.go_exchange_easier.backend.repository.CitiesRepository;
import com.go_exchange_easier.backend.service.CitiesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CitiesServiceImpl implements CitiesService {

    private final CitiesRepository citiesRepository;

    @Override
    public List<GetCityResponse> getByCountryId(short countryId) {
        List<City> cities = citiesRepository.findByCountryId(countryId);
        return cities.stream().map(c -> new GetCityResponse(
                c.getId(), c.getEnglishName(),
                new GetCityResponse.CountryDto(c.getCountry().getId(),
                        c.getCountry().getEnglishName())
               )).toList();
    }

}
