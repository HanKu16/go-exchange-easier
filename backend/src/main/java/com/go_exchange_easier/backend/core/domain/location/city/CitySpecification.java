package com.go_exchange_easier.backend.core.domain.location.city;

import com.go_exchange_easier.backend.core.domain.location.country.Country;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class CitySpecification {

    public static Specification<City> hasCountryId(short countryId) {
        return (root, query, cb) -> {
            Join<City, Country> countryJoin = root.join("country");
            return cb.equal(countryJoin.get("id"), countryId);
        };
    }

}
