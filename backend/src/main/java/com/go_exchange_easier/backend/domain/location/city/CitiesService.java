package com.go_exchange_easier.backend.domain.location.city;

import java.util.List;

public interface CitiesService {

    List<CityDetails> getAll(Short countryId);

}
