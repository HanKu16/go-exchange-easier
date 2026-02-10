package com.go_exchange_easier.backend.core.domain.exchange.impl;

import com.go_exchange_easier.backend.core.common.exception.NotOwnerOfResourceException;
import com.go_exchange_easier.backend.core.common.exception.ResourceNotFoundException;
import com.go_exchange_easier.backend.core.domain.exchange.Exchange;
import com.go_exchange_easier.backend.core.domain.exchange.ExchangeRepository;
import com.go_exchange_easier.backend.core.domain.exchange.ExchangeService;
import com.go_exchange_easier.backend.core.domain.exchange.ExchangeSpecification;
import com.go_exchange_easier.backend.core.domain.fieldofstudy.FieldOfStudy;
import com.go_exchange_easier.backend.core.domain.university.University;
import com.go_exchange_easier.backend.core.domain.user.User;
import com.go_exchange_easier.backend.core.domain.exchange.dto.ExchangeDetails;
import com.go_exchange_easier.backend.core.domain.exchange.dto.CreateExchangeRequest;
import com.go_exchange_easier.backend.core.common.exception.ReferencedResourceNotFoundException;
import com.go_exchange_easier.backend.core.domain.fieldofstudy.FieldOfStudyRepository;
import com.go_exchange_easier.backend.core.domain.university.UniversityRepository;
import com.go_exchange_easier.backend.core.domain.user.UserRepository;
import com.go_exchange_easier.backend.core.domain.exchange.dto.ExchangeFilters;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExchangeServiceImpl implements ExchangeService {

    private final FieldOfStudyRepository fieldOfStudyRepository;
    private final UniversityRepository universityRepository;
    private final ExchangeRepository exchangeRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<ExchangeDetails> getPage(ExchangeFilters filters, Pageable pageable) {
        Specification<Exchange> spec = ExchangeSpecification.fromFilter(filters);
        Page<Exchange> exchanges = exchangeRepository.findAll(spec, pageable);
        return exchanges.map(ExchangeDetails::fromEntity);
    }

    @Override
    @Transactional
    public ExchangeDetails create(int userId, CreateExchangeRequest request) {
        User userProxy = userRepository.getReferenceById(userId);
        University university = universityRepository
                .findById(request.universityId())
                .orElseThrow(() -> new ReferencedResourceNotFoundException(
                        "University of id " + request.universityId() +
                                " was not found."));
        FieldOfStudy fieldOfStudy = fieldOfStudyRepository
                .findById(request.fieldOfStudyId())
                .orElseThrow(() -> new ReferencedResourceNotFoundException(
                        "Field of study of id " + request.fieldOfStudyId() +
                                " was not found."));
        Exchange exchange = buildExchange(request, userProxy, university, fieldOfStudy);
        Exchange savedExchange = exchangeRepository.save(exchange);
        return ExchangeDetails.fromEntity(savedExchange);
    }

    @Override
    @Transactional
    public void delete(int exchangeId, int userId) {
        Exchange exchange = exchangeRepository.findById(exchangeId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Exchange of id " + exchangeId + " was not found."));
        if (!exchange.getUser().getId().equals(userId)) {
            throw new NotOwnerOfResourceException("Authenticated user is not " +
                    "entitled to delete exchange of id " + exchange + ".");
        }
        exchangeRepository.deleteById(exchangeId);
    }

    private Exchange buildExchange(CreateExchangeRequest request, User user,
            University university, FieldOfStudy fieldOfStudy) {
        Exchange exchange = new Exchange();
        exchange.setStartedAt(request.startedAt());
        exchange.setEndAt(request.endAt());
        exchange.setUser(user);
        exchange.setUniversity(university);
        exchange.setFieldOfStudy(fieldOfStudy);
        return exchange;
    }

}
