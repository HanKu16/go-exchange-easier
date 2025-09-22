package com.go_exchange_easier.backend.service.impl;

import com.go_exchange_easier.backend.dto.exchange.CreateExchangeRequest;
import com.go_exchange_easier.backend.dto.exchange.CreateExchangeResponse;
import com.go_exchange_easier.backend.dto.exchange.GetUserExchangeResponse;
import com.go_exchange_easier.backend.exception.*;
import com.go_exchange_easier.backend.exception.base.ReferencedResourceNotFoundException;
import com.go_exchange_easier.backend.exception.domain.ExchangeNotFoundException;
import com.go_exchange_easier.backend.model.*;
import com.go_exchange_easier.backend.repository.ExchangeRepository;
import com.go_exchange_easier.backend.repository.UniversityMajorRepository;
import com.go_exchange_easier.backend.repository.UniversityRepository;
import com.go_exchange_easier.backend.repository.UserRepository;
import com.go_exchange_easier.backend.service.ExchangeService;
import com.go_exchange_easier.backend.service.ResourceOwnershipChecker;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExchangeServiceImpl implements ExchangeService {

    private final ResourceOwnershipChecker resourceOwnershipChecker;
    private final UniversityMajorRepository universityMajorRepository;
    private final UniversityRepository universityRepository;
    private final ExchangeRepository exchangeRepository;
    private final UserRepository userRepository;

    @Override
    public List<GetUserExchangeResponse> getByUserId(int userId) {
        List<Object[]> rows = exchangeRepository.findByUserId(userId);
        List<GetUserExchangeResponse> exchanges = new ArrayList<>();

        for (Object[] row : rows) {
            Integer exchangeId = (Integer) row[0];
            LocalDate startedAt = ((Date) row[1]).toLocalDate();
            LocalDate endAt = ((Date) row[2]).toLocalDate();
            Short universityId = (Short) row[3];
            String universityOriginalName = (String) row[4];
            String universityEnglishName = (String) row[5];
            Integer cityId = (Integer) row[6];
            String cityName = (String) row[7];
            Short countryId = (Short) row[8];
            String countryName = (String) row[9];
            Short universityMajorId = (Short) row[10];
            String universityMajorName = (String) row[11];
            exchanges.add(new GetUserExchangeResponse(exchangeId,
                    new GetUserExchangeResponse.TimeRangeDto(startedAt, endAt),
                    new GetUserExchangeResponse.UniversityDto(universityId,
                            universityOriginalName, universityEnglishName),
                    new GetUserExchangeResponse.UniversityMajorDto(
                            universityMajorId, universityMajorName),
                    new GetUserExchangeResponse.CityDto(cityId, cityName,
                            new GetUserExchangeResponse.CountryDto(
                                    countryId, countryName))));
        }
        return exchanges;
    }

    @Override
    @Transactional
    public CreateExchangeResponse create(int userId, CreateExchangeRequest request) {
        if (!universityRepository.existsById(request.universityId())) {
            throw new ReferencedResourceNotFoundException("University of id " +
                    request.universityId() + " was not found.");
        }
        if (!universityMajorRepository.existsById(request.universityMajorId())) {
            throw new ReferencedResourceNotFoundException("University major " +
                    "of id " + request.universityMajorId() + " was not found.");
        }
        User user = userRepository.getReferenceById(userId);
        University university = universityRepository
                .getReferenceById(request.universityId());
        UniversityMajor major = universityMajorRepository
                .getReferenceById(request.universityMajorId());
        Exchange exchange = buildExchange(request, user, university, major);
        Exchange savedExchange = exchangeRepository.save(exchange);
        return buildCreateExchangeResponse(savedExchange);
    }

    @Override
    @Transactional
    public void delete(int exchangeId) {
        Exchange exchange = exchangeRepository.findById(exchangeId)
                .orElseThrow(() -> new ExchangeNotFoundException(
                        "Exchange of id " + exchangeId + " was not found."));
        if (!resourceOwnershipChecker.isOwner(exchange)) {
            throw new NotOwnerOfResourceException("Authenticated user is not " +
                    "entitled to delete exchange of id " + exchange + ".");
        }
        exchangeRepository.deleteById(exchangeId);
    }

    private Exchange buildExchange(CreateExchangeRequest request, User user,
            University university, UniversityMajor major) {
        Exchange exchange = new Exchange();
        exchange.setStartedAt(request.startedAt());
        exchange.setEndAt(request.endAt());
        exchange.setUser(user);
        exchange.setUniversity(university);
        exchange.setUniversityMajor(major);
        return exchange;
    }

    private CreateExchangeResponse buildCreateExchangeResponse(Exchange exchange) {
        return new CreateExchangeResponse(exchange.getId(),
                exchange.getStartedAt(), exchange.getEndAt(),
                exchange.getUser().getId(),
                exchange.getUniversity().getId(),
                exchange.getUniversityMajor().getId());
    }

}
