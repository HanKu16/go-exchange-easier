package com.go_exchange_easier.backend.domain.university.specification;

import com.go_exchange_easier.backend.common.jpa.SpecificationUtils;
import com.go_exchange_easier.backend.domain.location.City;
import com.go_exchange_easier.backend.domain.location.Country;
import com.go_exchange_easier.backend.domain.university.University;
import com.go_exchange_easier.backend.domain.university.dto.UniversityFilters;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class UniversitySpecification {

    public static Specification<University> fromFilter(UniversityFilters filter) {
        Specification<University> spec = (root, query, cb) -> null;
        spec = SpecificationUtils.append(spec, filter.englishName(),
                UniversitySpecification::hasEnglishName);
        spec = SpecificationUtils.append(spec, filter.nativeName(),
                UniversitySpecification::hasOriginalName);
        spec = SpecificationUtils.append(spec, filter.cityId(),
                UniversitySpecification::hasCityId);
        spec = SpecificationUtils.append(spec, filter.countryId(),
                UniversitySpecification::hasCountryId);
        return spec;
    }

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

    public static Specification<University> hasCountryId(short countryId) {
        return (root, query, cb) -> {
            Join<University, City> citiesJoin =
                    root.join("city");
            Join<City, Country> countriesJoin = citiesJoin.join("country");
            return cb.equal(countriesJoin.get("id"), countryId);
        };
    }

}
