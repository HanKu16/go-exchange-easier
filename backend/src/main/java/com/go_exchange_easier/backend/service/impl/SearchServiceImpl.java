package com.go_exchange_easier.backend.service.impl;

import com.go_exchange_easier.backend.dto.search.GetUniversityResponse;
import com.go_exchange_easier.backend.dto.search.GetUserByNickResponse;
import com.go_exchange_easier.backend.dto.search.GetUserByExchangeResponse;
import com.go_exchange_easier.backend.exception.domain.BadNumberOfSearchFiltersException;
import com.go_exchange_easier.backend.model.Exchange;
import com.go_exchange_easier.backend.model.University;
import com.go_exchange_easier.backend.model.User;
import com.go_exchange_easier.backend.repository.ExchangeRepository;
import com.go_exchange_easier.backend.repository.UniversityRepository;
import com.go_exchange_easier.backend.repository.UserRepository;
import com.go_exchange_easier.backend.repository.specification.ExchangeSpecification;
import com.go_exchange_easier.backend.repository.specification.UniversitySpecification;
import com.go_exchange_easier.backend.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final UniversityRepository universityRepository;
    private final ExchangeRepository exchangeRepository;
    private final UserRepository userRepository;


    @Override
    @Transactional(readOnly = true)
    public Page<GetUserByExchangeResponse> findUsersByExchange(
            Short universityId, Integer cityId, Short countryId,
            Short majorId, LocalDate startDate, LocalDate endDate,
            Pageable pageable) {
        int numberOfFiltersApplied = countMainFiltersApplied(Arrays.asList(
                universityId, cityId, countryId));
        if (numberOfFiltersApplied != 1) {
            throw new BadNumberOfSearchFiltersException(
                    "Only 1 filter among university, city and country " +
                            "can be applied, but " + numberOfFiltersApplied +
                            " were applied.");
        }
        Specification<Exchange> specification = buildSpecificationForFindByExchange(
                universityId, cityId, countryId, majorId,
                startDate, endDate);
        Page<Exchange> exchanges = exchangeRepository
                .findAll(specification, pageable);
        return exchanges.map(this::mapUserWithExchangeToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GetUserByNickResponse> findUsersByNick(String nick, Pageable pageable) {
        Page<User> users = userRepository.findByNick(nick, pageable);
        return users.map(this::mapUserToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GetUniversityResponse> findUniversities(
            String nativeName, String englishName,
            Integer cityId, Short countryId,
            Pageable pageable) {
        if ((cityId != null) && (countryId != null)) {
            throw new BadNumberOfSearchFiltersException(
                    "Only 1 filter among country and city can be applied, " +
                            "but both were applied.");
        }
        Specification<University> specification = buildSpecificationForFindUniversities(
                nativeName, englishName, cityId, countryId);
        Page<University> universities = universityRepository.findAll(specification, pageable);
        return universities.map(this::mapUniversityToResponse);
    }

    private Specification<Exchange> buildSpecificationForFindByExchange(
            Short universityId, Integer cityId,
            Short countryId, Short majorId,
            LocalDate startDate, LocalDate endDate) {
        Specification<Exchange> specification = ExchangeSpecification.fetchUser();
        if (universityId != null) {
            specification = specification.and(ExchangeSpecification
                    .hasUniversityId(universityId));
        } else if (countryId != null) {
            specification = specification.and(ExchangeSpecification
                    .hasCountryId(countryId));
        } else if (cityId != null) {
            specification = specification.and(ExchangeSpecification
                    .hasCityId(cityId));
        }
        if (majorId != null) {
            specification = specification.and(ExchangeSpecification
                    .hasMajorId(majorId));
        }
        if (startDate != null) {
            specification = specification.and(ExchangeSpecification
                    .hasStartDateAtLeast(startDate));
        }
        if (endDate != null) {
            specification = specification.and(ExchangeSpecification
                    .hasStartDateAtMost(endDate));
        }
        return specification;
    }

    private Specification<University> buildSpecificationForFindUniversities(
            String nativeName, String englishName, Integer cityId, Short countryId) {
        Specification<University> specification = (root, query, cb) -> null;
        if (nativeName != null) {
            specification = specification.and(UniversitySpecification
                    .hasOriginalName(nativeName));
        }
        if (englishName != null) {
            specification = specification.and(UniversitySpecification
                    .hasEnglishName(englishName));
        }
        if (cityId != null) {
            specification = specification.and(UniversitySpecification
                    .hasCityId(cityId));
        }
        if (countryId != null) {
            specification = specification.and(UniversitySpecification
                    .hasCountryId(countryId));
        }
        return specification;
    }

    private GetUserByExchangeResponse mapUserWithExchangeToResponse(Exchange exchange) {
        return new GetUserByExchangeResponse(exchange.getUser().getId(),
                exchange.getUser().getNick(),
                new GetUserByExchangeResponse.ExchangeDto(exchange.getId(),
                        new GetUserByExchangeResponse.TimeRangeDto(
                                exchange.getStartedAt(), exchange.getEndAt()),
                        new GetUserByExchangeResponse.UniversityDto(
                                exchange.getUniversity().getId(),
                                exchange.getUniversity().getOriginalName(),
                                exchange.getUniversity().getEnglishName(),
                                new GetUserByExchangeResponse.CityDto(
                                        exchange.getUniversity().getCity().getId(),
                                        exchange.getUniversity().getCity().getEnglishName(),
                                        new GetUserByExchangeResponse.CountryDto(
                                                exchange.getUniversity().getCity()
                                                        .getCountry().getId(),
                                                exchange.getUniversity().getCity()
                                                        .getCountry().getEnglishName()
                                        )
                                )), new GetUserByExchangeResponse.UniversityMajorDto(
                        exchange.getUniversityMajor().getId(), exchange.getUniversityMajor()
                        .getName())));
    }

    private GetUserByNickResponse mapUserToResponse(User user) {
        return new GetUserByNickResponse(user.getId(), user.getNick(),
                user.getCountryOfOrigin() != null ?
                        new GetUserByNickResponse.CountryDto(
                                user.getCountryOfOrigin().getId(),
                                user.getCountryOfOrigin().getEnglishName()) :
                        null,
                user.getHomeUniversity() != null ?
                        new GetUserByNickResponse.UniversityDto(
                                user.getHomeUniversity().getId(),
                                user.getHomeUniversity().getOriginalName(),
                                user.getHomeUniversity().getEnglishName()) :
                        null);
    }

    private GetUniversityResponse mapUniversityToResponse(University university) {
        return new GetUniversityResponse(university.getId(),
                university.getOriginalName(), university.getEnglishName(),
                new GetUniversityResponse.CityDto(
                university.getCity().getId(), university.getEnglishName(),
                new GetUniversityResponse.CountryDto(
                        university.getCity().getCountry().getId(),
                        university.getCity().getCountry().getEnglishName())));
    }

    private int countMainFiltersApplied(List<Object> filters) {
        return (int) filters.stream().filter(Objects::nonNull).count();
    }

}
