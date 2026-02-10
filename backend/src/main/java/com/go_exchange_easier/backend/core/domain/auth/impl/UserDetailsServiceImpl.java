package com.go_exchange_easier.backend.core.domain.auth.impl;

import com.go_exchange_easier.backend.core.domain.auth.dto.AuthenticatedUser;
import com.go_exchange_easier.backend.core.domain.auth.entity.UserCredentials;
import com.go_exchange_easier.backend.core.domain.auth.UserCredentialsRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserCredentialsRepository userCredentialsRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        UserCredentials credentials = userCredentialsRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User of username " + username + " was not found."));
        int userId = credentials.getUser().getId();
        boolean enabled = credentials.getUser().getDeletedAt() == null;
        return new AuthenticatedUser(userId, credentials.getUsername(),
                credentials.getPassword(), enabled, credentials.getRoles());
    }

}
