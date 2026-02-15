package com.go_exchange_easier.backend.core.domain.location.country;

import java.util.List;

public interface CountryService {

    List<CountryDetails> getAll();
    String getFlagUrl(String flagKey);

}
