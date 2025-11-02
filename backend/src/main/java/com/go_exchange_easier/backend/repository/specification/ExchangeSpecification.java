package com.go_exchange_easier.backend.repository.specification;

import com.go_exchange_easier.backend.model.*;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDate;

public class ExchangeSpecification {

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

    public static Specification<Exchange> hasCountryId(int countryId) {
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

    public static Specification<Exchange> hasDatesRangeBetween(
            LocalDate rangeStartDate, LocalDate rangeEndDate) {
        return (root, query, cb) -> {
            Path<LocalDate> exchangeStartDatePath = root.get("startDate");
            Predicate startPredicate = cb.greaterThanOrEqualTo(
                    exchangeStartDatePath, rangeStartDate);
            Predicate endPredicate = cb.lessThanOrEqualTo(
                    exchangeStartDatePath, rangeEndDate);
            return cb.and(startPredicate, endPredicate);
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
