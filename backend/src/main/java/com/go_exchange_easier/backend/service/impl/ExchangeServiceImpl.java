package com.go_exchange_easier.backend.service.impl;

import com.go_exchange_easier.backend.dto.exchange.CreateExchangeRequest;
import com.go_exchange_easier.backend.dto.exchange.CreateExchangeResponse;
import com.go_exchange_easier.backend.exception.*;
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

@Service
@RequiredArgsConstructor
public class ExchangeServiceImpl implements ExchangeService {

    private final ResourceOwnershipChecker resourceOwnershipChecker;
    private final UniversityMajorRepository universityMajorRepository;
    private final UniversityRepository universityRepository;
    private final ExchangeRepository exchangeRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public CreateExchangeResponse create(CreateExchangeRequest request) {
        if (!userRepository.existsById(request.userId())) {
            throw new UserDoesNotExistException("User of id " +
                    request.userId() + " does not exist.");
        }
        if (!universityRepository.existsById(request.universityId())) {
            throw new UniversityDoesNotExistException("University of id " +
                    request.userId() + " does not exist.");
        }
        if (!universityMajorRepository.existsById(request.universityMajorId())) {
            throw new UniversityMajorDoesNotExistException("Major of id " +
                    request.universityMajorId() + " does not exist.");
        }
        User user = userRepository.getReferenceById(request.userId());
        University university = universityRepository
                .getReferenceById(request.universityId());
        UniversityMajor major = universityMajorRepository
                .getReferenceById(request.universityMajorId());
        Exchange exchange = buildExchangeEntity(request, user, university, major);
        Exchange savedExchange = exchangeRepository.save(exchange);
        return buildCreateExchangeResponse(savedExchange);
    }

    @Override
    @Transactional
    public void delete(int exchangeId) {
        Exchange exchange = exchangeRepository.findById(exchangeId)
                .orElseThrow(() -> new ExchangeDoesNotExistException(
                        "Exchange of id " + exchangeId + " does not exist."));
        if (!resourceOwnershipChecker.isOwner(exchange)) {
            throw new NotOwnerOfResourceException("You can can not " +
                    "delete exchange that you are not owner of.");
        }
        exchangeRepository.deleteById(exchangeId);
    }

    private Exchange buildExchangeEntity(CreateExchangeRequest request,
            User user, University university, UniversityMajor major) {
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
