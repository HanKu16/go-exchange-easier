package com.go_exchange_easier.backend.service.impl;

import com.go_exchange_easier.backend.dto.user.UserRegistrationRequest;
import com.go_exchange_easier.backend.dto.user.UserRegistrationResponse;
import com.go_exchange_easier.backend.exception.domain.MissingDefaultRoleException;
import com.go_exchange_easier.backend.exception.UsernameAlreadyExistsException;
import com.go_exchange_easier.backend.model.*;
import com.go_exchange_easier.backend.repository.*;
import com.go_exchange_easier.backend.service.UserRegistrar;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.OffsetDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRegistrarImpl implements UserRegistrar {

    private final UserCredentialsRepository userCredentialsRepository;
    private final UserDescriptionRepository userDescriptionRepository;
    private final UserNotificationRepository userNotificationRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserRegistrationResponse register(UserRegistrationRequest request) {
        boolean doesUserOfGivenUsernameExists = userCredentialsRepository
                .existsByUsername(request.login());
        if (doesUserOfGivenUsernameExists) {
            throw new UsernameAlreadyExistsException("User of login " +
                    request.login() + " already exists.");
        }
        UserCredentials credentials = buildCredentials(request);
        assignRoles(credentials);
        UserCredentials savedCredentials = userCredentialsRepository.save(credentials);
        UserDescription description = buildDescription();
        UserDescription savedDescription = userDescriptionRepository.save(description);
        UserNotification notification = buildNotification(request);
        UserNotification savedNotification = userNotificationRepository.save(notification);
        User user = buildUser(request, savedCredentials,
                savedDescription, savedNotification);
        User savedUser = userRepository.save(user);
        return new UserRegistrationResponse(savedUser.getId(), credentials.getUsername(),
                savedUser.getNick(), savedUser.getCreatedAt());
    }

    private User buildUser(UserRegistrationRequest request, UserCredentials credentials,
                           UserDescription description, UserNotification notification) {
        User user = new User();
        user.setNick(request.nick() != null ? request.nick() : request.login());
        user.setCreatedAt(OffsetDateTime.now());
        user.setCredentials(credentials);
        user.setDescription(description);
        user.setNotification(notification);
        return user;
    }

    private UserCredentials buildCredentials(UserRegistrationRequest request) {
        String encodedPassword = passwordEncoder.encode(request.password());
        UserCredentials credentials = new UserCredentials();
        credentials.setUsername(request.login());
        credentials.setPassword(encodedPassword);
        credentials.setEnabled(true);
        return credentials;
    }

    private UserDescription buildDescription() {
        UserDescription description = new UserDescription();
        description.setTextContent("");
        return description;
    }

    private UserNotification buildNotification(UserRegistrationRequest request) {
        UserNotification notification = new UserNotification();
        notification.setMail(request.mail());
        notification.setMailNotificationEnabled(request.mail() != null);
        return notification;
    }

    private void assignRoles(UserCredentials credentials) {
        Optional<Role> role = roleRepository.findByName(RoleName.ROLE_USER.name());
        if (role.isEmpty()) {
            throw new MissingDefaultRoleException("Default role of name " +
                    RoleName.ROLE_USER.name() + " was not found.");
        }
        credentials.getRoles().add(role.get());
    }

}
