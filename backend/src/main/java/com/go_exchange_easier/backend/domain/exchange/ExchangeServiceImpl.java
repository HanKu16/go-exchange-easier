package com.go_exchange_easier.backend.domain.exchange;

import com.go_exchange_easier.backend.common.exception.NotOwnerOfResourceException;
import com.go_exchange_easier.backend.domain.fieldofstudy.UniversityMajor;
import com.go_exchange_easier.backend.domain.university.University;
import com.go_exchange_easier.backend.domain.user.User;
import com.go_exchange_easier.backend.domain.exchange.dto.ExchangeDetails;
import com.go_exchange_easier.backend.domain.exchange.dto.CreateExchangeRequest;
import com.go_exchange_easier.backend.domain.exchange.dto.CreateExchangeResponse;
import com.go_exchange_easier.backend.common.exception.ReferencedResourceNotFoundException;
import com.go_exchange_easier.backend.domain.fieldofstudy.UniversityMajorRepository;
import com.go_exchange_easier.backend.domain.university.UniversityRepository;
import com.go_exchange_easier.backend.domain.user.UserRepository;
import com.go_exchange_easier.backend.domain.exchange.dto.ExchangeFilters;
import com.go_exchange_easier.backend.domain.exchange.specification.ExchangeSpecification;
import com.go_exchange_easier.backend.service.ResourceOwnershipChecker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
    @Transactional(readOnly = true)
    public Page<ExchangeDetails> getPage(ExchangeFilters filters, Pageable pageable) {
        Specification<Exchange> spec = ExchangeSpecification.fromFilter(filters);
        Page<Exchange> exchanges = exchangeRepository.findAll(spec, pageable);
        return exchanges.map(ExchangeDetails::fromEntity);
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
