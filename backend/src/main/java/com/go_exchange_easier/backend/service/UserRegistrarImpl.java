package com.go_exchange_easier.backend.service;

import com.go_exchange_easier.backend.dto.user.UserRegistrationRequest;
import com.go_exchange_easier.backend.dto.user.UserRegistrationResponse;
import com.go_exchange_easier.backend.exception.UsernameAlreadyExistsException;
import com.go_exchange_easier.backend.model.User;
import com.go_exchange_easier.backend.model.UserCredentials;
import com.go_exchange_easier.backend.model.UserDescription;
import com.go_exchange_easier.backend.model.UserNotification;
import com.go_exchange_easier.backend.repository.UserCredentialsRepository;
import com.go_exchange_easier.backend.repository.UserDescriptionRepository;
import com.go_exchange_easier.backend.repository.UserNotificationRepository;
import com.go_exchange_easier.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class UserRegistrarImpl implements UserRegistrar {

    private final UserCredentialsRepository userCredentialsRepository;
    private final UserDescriptionRepository userDescriptionRepository;
    private final UserNotificationRepository userNotificationRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserRegistrationResponse register(UserRegistrationRequest request) {
        boolean doesUserOfGivenUsernameExists = userCredentialsRepository
                .existsByUsername(request.login());
        if (doesUserOfGivenUsernameExists) {
            throw new UsernameAlreadyExistsException("User of login " +
                    request.login() + " already exists.");
        }
        User user = createUser(request);
        UserCredentials credentials = createCredentials(user, request);
        UserDescription description = createDescription(user);
        UserNotification notification = createNotification(user, request);
        return new UserRegistrationResponse(user.getId(), credentials.getUsername(),
                user.getNick(), user.getCreatedAt());
    }

    private User createUser(UserRegistrationRequest request) {
        User user = new User();
        user.setNick(request.nick() != null ? request.nick() : request.login());
        user.setCreatedAt(OffsetDateTime.now());
        return userRepository.save(user);
    }

    private UserCredentials createCredentials(
            User user, UserRegistrationRequest request) {
        UserCredentials credentials = new UserCredentials();
        credentials.setUser(user);
        credentials.setUsername(request.login());
        credentials.setPassword(request.password());
        credentials.setEnabled(true);
        return userCredentialsRepository.save(credentials);
    }

    private UserDescription createDescription(User user) {
        UserDescription description = new UserDescription();
        description.setUser(user);
        description.setTextContent("");
        return userDescriptionRepository.save(description);
    }

    private UserNotification createNotification(
            User user, UserRegistrationRequest request) {
        UserNotification notification = new UserNotification();
        notification.setUser(user);
        notification.setMail(request.mail());
        notification.setMailNotificationEnabled(request.mail() != null);
        return userNotificationRepository.save(notification);
    }

}
