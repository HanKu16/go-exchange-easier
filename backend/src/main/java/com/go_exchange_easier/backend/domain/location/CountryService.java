package com.go_exchange_easier.backend.domain.location;

import com.go_exchange_easier.backend.domain.location.dto.CountryDetails;
import java.util.List;

public interface CountryService {

    List<CountryDetails> getAll();

}
