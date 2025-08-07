package com.go_exchange_easier.backend.repository;

import com.go_exchange_easier.backend.model.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRepository extends
        JpaRepository<Exchange, Integer> { }
