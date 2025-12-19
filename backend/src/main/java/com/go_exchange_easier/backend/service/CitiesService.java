package com.go_exchange_easier.backend.service;

import com.go_exchange_easier.backend.dto.details.CityDetails;
import java.util.List;

public interface CitiesService {

    List<CityDetails> getAll(Short countryId);

}
