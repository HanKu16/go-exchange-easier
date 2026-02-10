package com.go_exchange_easier.backend.core.domain.location.city;

import java.util.List;

public interface CitiesService {

    List<CityDetails> getAll(Short countryId);

}
