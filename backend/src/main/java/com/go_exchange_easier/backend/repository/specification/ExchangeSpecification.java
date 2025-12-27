package com.go_exchange_easier.backend.repository.specification;

import com.go_exchange_easier.backend.model.*;
import com.go_exchange_easier.backend.dto.filter.ExchangeFilters;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Path;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDate;

public class ExchangeSpecification {

    public static Specification<Exchange> fromFilter(ExchangeFilters filter) {
        Specification<Exchange> spec = ExchangeSpecification.fetchUser();
        spec = SpecificationUtils.append(spec, filter.universityId(),
                ExchangeSpecification::hasUniversityId);
        spec = SpecificationUtils.append(spec, filter.countryId(),
                ExchangeSpecification::hasCountryId);
        spec = SpecificationUtils.append(spec, filter.cityId(),
                ExchangeSpecification::hasCityId);
        spec = SpecificationUtils.append(spec, filter.majorId(),
                ExchangeSpecification::hasMajorId);
        spec = SpecificationUtils.append(spec, filter.startDate(),
                ExchangeSpecification::hasStartDateAtLeast);
        spec = SpecificationUtils.append(spec, filter.endDate(),
                ExchangeSpecification::hasStartDateAtMost);
        spec = SpecificationUtils.append(spec, filter.userId(),
                ExchangeSpecification::hasUserId);
        return spec;
    }

    public static Specification<Exchange> hasUniversityId(short universityId) {
        return (root, query, cb) -> {
            Join<Exchange, University> universitiesJoin =
                    root.join("university");
            return cb.equal(universitiesJoin.get("id"),
                    universityId);
        };
    }

    public static Specification<Exchange> hasCityId(int cityId) {
        return (root, query, cb) -> {
            Join<Exchange, University> universitiesJoin =
                    root.join("university");
            Join<University, City> citiesJoin = universitiesJoin.join("city");
            return cb.equal(citiesJoin.get("id"), cityId);
        };
    }

    public static Specification<Exchange> hasCountryId(short countryId) {
        return (root, query, cb) -> {
            Join<Exchange, University> universitiesJoin =
                    root.join("university");
            Join<University, City> citiesJoin = universitiesJoin.join("city");
            Join<City, Country> countriesJoin = citiesJoin.join("country");
            return cb.equal(countriesJoin.get("id"), countryId);
        };
    }

    public static Specification<Exchange> hasMajorId(short majorId) {
        return (root, query, cb) -> {
            Join<Exchange, UniversityMajor> majorsJoin =
                    root.join("universityMajor");
            return cb.equal(majorsJoin.get("id"), majorId);
        };
    }

    public static Specification<Exchange> hasStartDateAtLeast(
            LocalDate rangeStartDate) {
        return (root, query, cb) -> {
            Path<LocalDate> exchangeStartDatePath = root.get("startedAt");
            return cb.greaterThanOrEqualTo(exchangeStartDatePath, rangeStartDate);
        };
    }

    public static Specification<Exchange> hasStartDateAtMost(
            LocalDate rangeEndDate) {
        return (root, query, cb) -> {
            Path<LocalDate> exchangeStartDatePath = root.get("startedAt");
            return cb.lessThanOrEqualTo(exchangeStartDatePath, rangeEndDate);
        };
    }

    public static Specification<Exchange> hasUserId(int userId) {
        return (root, query, cb) -> {
            Join<Exchange, User> userJoin = root.join("user");
            return cb.equal(userJoin.get("id"), userId);
        };
    }

    public static Specification<Exchange> fetchUser() {
        return (root, query, cb) -> {
            if ((query.getResultType() != Long.class) &&
                    (query.getResultType() != long.class)) {
                root.fetch("user");
            }
            return null;
        };
    }

}
