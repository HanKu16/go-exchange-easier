package com.go_exchange_easier.backend.core.domain.auth.impl;

import com.go_exchange_easier.backend.core.domain.auth.PrincipalRepository;
import com.go_exchange_easier.backend.core.domain.auth.UserRegistrar;
import com.go_exchange_easier.backend.core.domain.auth.dto.RegistrationRequest;
import com.go_exchange_easier.backend.core.domain.auth.dto.RegistrationSummary;
import com.go_exchange_easier.backend.core.domain.auth.entity.Principal;
import com.go_exchange_easier.backend.core.domain.auth.entity.Role;
import com.go_exchange_easier.backend.core.domain.auth.event.UserRegisteredEvent;
import com.go_exchange_easier.backend.core.domain.auth.exception.MailAlreadyExistsException;
import com.go_exchange_easier.backend.core.domain.auth.exception.UsernameAlreadyExistsException;
import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserRegistrarImpl implements UserRegistrar {

    private final ApplicationEventPublisher eventPublisher;
    private final PrincipalRepository principalRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public RegistrationSummary register(RegistrationRequest request) {
        boolean doesUserOfGivenUsernameExists = principalRepository.existsByUsername(request.login());
        if (doesUserOfGivenUsernameExists) {
            throw new UsernameAlreadyExistsException("User of login " + request.login() + " already exists.");
        }
        if (request.mail() != null) {
            boolean doesUserOfGivenMailExists = principalRepository.existsByMail(request.mail());
            if (doesUserOfGivenMailExists) {
                throw new MailAlreadyExistsException("User of mail " + request.mail() + " already exists.");
            }
        }
        Principal principal = buildPrincipal(request);
        Principal savedPrincipal = principalRepository.save(principal);
        String nick = request.nick() == null ? request.login() : request.nick();
        OffsetDateTime createdAt = OffsetDateTime.now();
        eventPublisher.publishEvent(new UserRegisteredEvent(
                savedPrincipal.getId(),
                nick,
                request.mail(),
                createdAt
        ));
        return new RegistrationSummary(savedPrincipal.getId(), principal.getUsername(), nick, createdAt);
    }

    private Principal buildPrincipal(RegistrationRequest request) {
        String encodedPassword = passwordEncoder.encode(request.password());
        Principal principal = new Principal();
        principal.setUsername(request.login());
        principal.setPassword(encodedPassword);
        principal.getRoles()
                .add(Role.ROLE_USER);
        return principal;
    }

}
