package com.go_exchange_easier.backend.service;

import com.go_exchange_easier.backend.dto.country.GetCountryResponse;
import java.util.List;

public interface CountryService {

    List<GetCountryResponse> getAll();

}
