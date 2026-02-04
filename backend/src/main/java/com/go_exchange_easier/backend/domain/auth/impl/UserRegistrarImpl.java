package com.go_exchange_easier.backend.domain.auth.impl;

import com.go_exchange_easier.backend.domain.auth.*;
import com.go_exchange_easier.backend.domain.auth.entity.Role;
import com.go_exchange_easier.backend.domain.auth.entity.UserCredentials;
import com.go_exchange_easier.backend.domain.user.*;
import com.go_exchange_easier.backend.domain.auth.dto.RegistrationRequest;
import com.go_exchange_easier.backend.domain.auth.dto.RegistrationSummary;
import com.go_exchange_easier.backend.domain.user.description.UserDescription;
import com.go_exchange_easier.backend.domain.user.description.UserDescriptionRepository;
import com.go_exchange_easier.backend.domain.user.notification.UserNotification;
import com.go_exchange_easier.backend.domain.user.notification.UserNotificationRepository;
import com.go_exchange_easier.backend.domain.auth.exception.MailAlreadyExistsException;
import com.go_exchange_easier.backend.domain.auth.exception.UsernameAlreadyExistsException;
import com.go_exchange_easier.backend.domain.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class UserRegistrarImpl implements UserRegistrar {

    private final UserCredentialsRepository userCredentialsRepository;
    private final UserDescriptionRepository userDescriptionRepository;
    private final UserNotificationRepository userNotificationRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public RegistrationSummary register(RegistrationRequest request) {
        boolean doesUserOfGivenUsernameExists = userCredentialsRepository
                .existsByUsername(request.login());
        if (doesUserOfGivenUsernameExists) {
            throw new UsernameAlreadyExistsException("User of login " +
                    request.login() + " already exists.");
        }
        boolean doesUserOfGivenMailExists = userNotificationRepository
                .existsByMail(request.mail());
        if (doesUserOfGivenMailExists) {
            throw new MailAlreadyExistsException("User of mail " +
                    request.mail() + " already exists.");
        }
        UserCredentials credentials = buildCredentials(request);
        credentials.getRoles().add(Role.RoleUser);
        UserCredentials savedCredentials = userCredentialsRepository.save(credentials);
        UserDescription description = buildDescription();
        UserDescription savedDescription = userDescriptionRepository.save(description);
        UserNotification notification = buildNotification(request);
        UserNotification savedNotification = userNotificationRepository.save(notification);
        User user = buildUser(request, savedCredentials,
                savedDescription, savedNotification);
        User savedUser = userRepository.save(user);
        return new RegistrationSummary(savedUser.getId(), credentials.getUsername(),
                savedUser.getNick(), savedUser.getCreatedAt());
    }

    private User buildUser(RegistrationRequest request, UserCredentials credentials,
                           UserDescription description, UserNotification notification) {
        User user = new User();
        user.setNick(request.nick() != null ? request.nick() : request.login());
        user.setCreatedAt(OffsetDateTime.now());
        user.setCredentials(credentials);
        user.setDescription(description);
        user.setNotification(notification);
        return user;
    }

    private UserCredentials buildCredentials(RegistrationRequest request) {
        String encodedPassword = passwordEncoder.encode(request.password());
        UserCredentials credentials = new UserCredentials();
        credentials.setUsername(request.login());
        credentials.setPassword(encodedPassword);
        return credentials;
    }

    private UserDescription buildDescription() {
        UserDescription description = new UserDescription();
        description.setTextContent("");
        return description;
    }

    private UserNotification buildNotification(RegistrationRequest request) {
        UserNotification notification = new UserNotification();
        String mail = (request.mail() == null || request.mail().isBlank()) ?
                null : request.mail();
        System.out.println(mail);
        boolean isMailNotificationEnabled = mail != null;
        notification.setMail(mail);
        notification.setMailNotificationEnabled(isMailNotificationEnabled);
        return notification;
    }

}
