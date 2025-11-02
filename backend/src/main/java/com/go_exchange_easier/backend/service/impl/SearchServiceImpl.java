package com.go_exchange_easier.backend.service.impl;

import com.go_exchange_easier.backend.dto.search.GetUsersByExchangeResponse;
import com.go_exchange_easier.backend.exception.domain.BadNumberOfSearchFiltersException;
import com.go_exchange_easier.backend.model.Exchange;
import com.go_exchange_easier.backend.repository.ExchangeRepository;
import com.go_exchange_easier.backend.repository.UserRepository;
import com.go_exchange_easier.backend.repository.specification.ExchangeSpecification;
import com.go_exchange_easier.backend.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final ExchangeRepository exchangeRepository;
    private final UserRepository userRepository;


    @Override
    @Transactional(readOnly = true)
    public Page<GetUsersByExchangeResponse> findUsersByExchange(
            Short universityId, Integer cityId, Short countryId,
            Short majorId, Pageable pageable) {
        int numberOfFiltersApplied = countMainFiltersApplied(Arrays.asList(
                universityId, cityId, countryId));
        if (numberOfFiltersApplied != 1) {
            throw new BadNumberOfSearchFiltersException(
                    "Only 1 filter can be applied, but " +
                            numberOfFiltersApplied + " were applied.");
        }
        Specification<Exchange> specification = buildSpecification(
                universityId, cityId, countryId, majorId);
        Page<Exchange> exchanges = exchangeRepository
                .findAll(specification, pageable);
        return exchanges.map(this::mapUserWithExchangeToResponse);
    }

    private Specification<Exchange> buildSpecification(
            Short universityId, Integer cityId,
            Short countryId, Short majorId) {
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
        return specification;
    }

    private GetUsersByExchangeResponse mapUserWithExchangeToResponse(Exchange exchange) {
        return new GetUsersByExchangeResponse(exchange.getUser().getId(),
                exchange.getUser().getNick(),
                new GetUsersByExchangeResponse.ExchangeDto(exchange.getId(),
                        new GetUsersByExchangeResponse.TimeRangeDto(
                                exchange.getStartedAt(), exchange.getEndAt()),
                        new GetUsersByExchangeResponse.UniversityDto(
                                exchange.getUniversity().getId(),
                                exchange.getUniversity().getOriginalName(),
                                exchange.getUniversity().getEnglishName(),
                                new GetUsersByExchangeResponse.CityDto(
                                        exchange.getUniversity().getCity().getId(),
                                        exchange.getUniversity().getCity().getEnglishName(),
                                        new GetUsersByExchangeResponse.CountryDto(
                                                exchange.getUniversity().getCity()
                                                        .getCountry().getId(),
                                                exchange.getUniversity().getCity()
                                                        .getCountry().getEnglishName()
                                        )
                                )), new GetUsersByExchangeResponse.UniversityMajorDto(
                        exchange.getUniversityMajor().getId(), exchange.getUniversityMajor()
                        .getName())));
    }

    private int countMainFiltersApplied(List<Object> filters) {
        return (int) filters.stream().filter(Objects::nonNull).count();
    }

}
