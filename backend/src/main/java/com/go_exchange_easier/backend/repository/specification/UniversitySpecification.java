package com.go_exchange_easier.backend.repository.specification;

import com.go_exchange_easier.backend.model.*;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class UniversitySpecification {

    public static Specification<University> hasOriginalName(
            String originalName) {
        return (root, query, cb) -> {
            String searchPattern = "%" + originalName.toLowerCase() + "%";
            return cb.like(cb.lower(root.get("originalName")),
                    searchPattern
            );
        };
    }

    public static Specification<University> hasEnglishName(
            String englishName) {
        return (root, query, cb) -> {
            String searchPattern = "%" + englishName.toLowerCase() + "%";
            return cb.like(cb.lower(root.get("englishName")),
                    searchPattern
            );
        };
    }

    public static Specification<University> hasCityId(int cityId) {
        return (root, query, cb) -> {
            Join<University, City> citiesJoin =
                    root.join("city");
            return cb.equal(citiesJoin.get("id"), cityId);
        };
    }

    public static Specification<University> hasCountryId(int countryId) {
        return (root, query, cb) -> {
            Join<University, City> citiesJoin =
                    root.join("city");
            Join<City, Country> countriesJoin = citiesJoin.join("country");
            return cb.equal(countriesJoin.get("id"), countryId);
        };
    }

}
