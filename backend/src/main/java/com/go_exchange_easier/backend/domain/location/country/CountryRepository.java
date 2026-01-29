package com.go_exchange_easier.backend.domain.location.country;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends
        JpaRepository<Country, Short> { }
