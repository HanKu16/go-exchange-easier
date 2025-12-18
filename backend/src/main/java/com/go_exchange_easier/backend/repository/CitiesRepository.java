package com.go_exchange_easier.backend.repository;

import com.go_exchange_easier.backend.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CitiesRepository extends
        JpaRepository<City, Integer>,
        JpaSpecificationExecutor<City> { }
