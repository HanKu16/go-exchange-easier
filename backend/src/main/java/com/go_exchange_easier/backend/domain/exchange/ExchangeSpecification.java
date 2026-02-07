package com.go_exchange_easier.backend.domain.exchange;

import com.go_exchange_easier.backend.common.jpa.SpecificationUtils;
import com.go_exchange_easier.backend.domain.fieldofstudy.FieldOfStudy;
import com.go_exchange_easier.backend.domain.location.city.City;
import com.go_exchange_easier.backend.domain.location.country.Country;
import com.go_exchange_easier.backend.domain.university.University;
import com.go_exchange_easier.backend.domain.user.User;
import com.go_exchange_easier.backend.domain.exchange.dto.ExchangeFilters;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
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
        spec = SpecificationUtils.append(spec, filter.fieldOfStudyId(),
                ExchangeSpecification::hasFieldOfStudyId);
        spec = SpecificationUtils.append(spec, filter.startDate(),
                ExchangeSpecification::hasStartDateAtLeast);
        spec = SpecificationUtils.append(spec, filter.endDate(),
                ExchangeSpecification::hasEndDateAtMost);
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

    public static Specification<Exchange> hasFieldOfStudyId(short fieldOfStudyId) {
        return (root, query, cb) -> {
            Join<Exchange, FieldOfStudy> fieldsOfStudy =
                    root.join("fieldOfStudy");
            return cb.equal(fieldsOfStudy.get("id"), fieldOfStudyId);
        };
    }

    public static Specification<Exchange> hasStartDateAtLeast(
            LocalDate rangeStartDate) {
        return (root, query, cb) -> {
            Path<LocalDate> exchangeStartDatePath = root.get("startedAt");
            return cb.greaterThanOrEqualTo(exchangeStartDatePath, rangeStartDate);
        };
    }

    public static Specification<Exchange> hasEndDateAtMost(
            LocalDate rangeEndDate) {
        return (root, query, cb) -> {
            Path<LocalDate> exchangeEndDatePath = root.get("endAt");
            return cb.lessThanOrEqualTo(exchangeEndDatePath, rangeEndDate);
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
            Join<Exchange, User> userJoin = root.join("user", JoinType.INNER);
            return cb.isNull(userJoin.get("deletedAt"));
        };
    }

}
