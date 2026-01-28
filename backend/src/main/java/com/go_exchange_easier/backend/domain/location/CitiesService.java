package com.go_exchange_easier.backend.domain.location;

import com.go_exchange_easier.backend.domain.location.dto.CityDetails;
import java.util.List;

public interface CitiesService {

    List<CityDetails> getAll(Short countryId);

}
