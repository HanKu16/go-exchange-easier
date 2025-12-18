package com.go_exchange_easier.backend.service;

import com.go_exchange_easier.backend.dto.city.GetCityResponse;
import java.util.List;

public interface CitiesService {

    List<GetCityResponse> getAll(Short countryId);

}
