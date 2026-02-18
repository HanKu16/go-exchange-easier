package com.go_exchange_easier.backend.core.domain.auth.impl;

import com.go_exchange_easier.backend.core.domain.auth.UserCredentialsRepository;
import com.go_exchange_easier.backend.core.domain.auth.UserRegistrar;
import com.go_exchange_easier.backend.core.domain.user.UserRepository;
import com.go_exchange_easier.backend.core.domain.auth.entity.Role;
import com.go_exchange_easier.backend.core.domain.auth.entity.UserCredentials;
import com.go_exchange_easier.backend.core.domain.auth.dto.RegistrationRequest;
import com.go_exchange_easier.backend.core.domain.auth.dto.RegistrationSummary;
import com.go_exchange_easier.backend.core.domain.user.description.UserDescription;
import com.go_exchange_easier.backend.core.domain.user.description.UserDescriptionRepository;
import com.go_exchange_easier.backend.core.domain.user.notification.NotificationSettings;
import com.go_exchange_easier.backend.core.domain.user.notification.UserNotificationRepository;
import com.go_exchange_easier.backend.core.domain.auth.exception.MailAlreadyExistsException;
import com.go_exchange_easier.backend.core.domain.auth.exception.UsernameAlreadyExistsException;
import com.go_exchange_easier.backend.core.domain.user.User;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
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
        if (request.mail() != null) {
            boolean doesUserOfGivenMailExists = userNotificationRepository
                    .existsByMail(request.mail());
            if (doesUserOfGivenMailExists) {
                throw new MailAlreadyExistsException("User of mail " +
                        request.mail() + " already exists.");
            }
        }
        User user = buildUser(request);
        User savedUser = userRepository.save(user);
        UserCredentials credentials = buildCredentials(request, user);
        credentials.getRoles().add(Role.RoleUser);
        UserCredentials savedCredentials = userCredentialsRepository.save(credentials);
        UserDescription description = buildDescription(user);
        UserDescription savedDescription = userDescriptionRepository.save(description);
        NotificationSettings notification = buildNotification(request, user);
        NotificationSettings savedNotification = userNotificationRepository.save(notification);
        return new RegistrationSummary(savedUser.getId(), credentials.getUsername(),
                savedUser.getNick(), savedUser.getCreatedAt());
    }

    private User buildUser(RegistrationRequest request) {
        User user = new User();
        user.setNick(request.nick() != null ? request.nick() : request.login());
        user.setCreatedAt(OffsetDateTime.now());
        return user;
    }

    private UserCredentials buildCredentials(RegistrationRequest request, User user) {
        String encodedPassword = passwordEncoder.encode(request.password());
        UserCredentials credentials = new UserCredentials();
        credentials.setUsername(request.login());
        credentials.setPassword(encodedPassword);
        credentials.setUser(user);
        return credentials;
    }

    private UserDescription buildDescription(User user) {
        UserDescription description = new UserDescription();
        description.setTextContent("");
        description.setUser(user);
        return description;
    }

    private NotificationSettings buildNotification(RegistrationRequest request, User user) {
        NotificationSettings notification = new NotificationSettings();
        String mail = (request.mail() == null || request.mail().isBlank()) ?
                null : request.mail();
        System.out.println(mail);
        boolean isMailNotificationEnabled = mail != null;
        notification.setMail(mail);
        notification.setMailNotificationEnabled(isMailNotificationEnabled);
        notification.setUser(user);
        return notification;
    }

}
