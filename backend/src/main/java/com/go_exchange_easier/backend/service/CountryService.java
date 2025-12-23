package com.go_exchange_easier.backend.service;

import com.go_exchange_easier.backend.dto.details.CountryDetails;
import java.util.List;

public interface CountryService {

    List<CountryDetails> getAll();

}
