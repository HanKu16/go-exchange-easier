package com.go_exchange_easier.backend.repository;

import com.go_exchange_easier.backend.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CitiesRepository extends
        JpaRepository<City, Integer> {

    @Query("""
            SELECT ci FROM City ci
            JOIN FETCH ci.country co
            WHERE co.id = :countryId
            """)
    List<City> findByCountryId(Short countryId);

}
