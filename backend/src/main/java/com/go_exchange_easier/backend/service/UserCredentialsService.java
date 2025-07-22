package com.go_exchange_easier.backend.service;

import com.go_exchange_easier.backend.model.UserCredentials;
import com.go_exchange_easier.backend.repository.UserCredentialsRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserCredentialsService implements UserDetailsService {

    private final UserCredentialsRepository userCredentialsRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Optional<UserCredentials> userCredentials = userCredentialsRepository
                .findByUsername(username);
        return userCredentials.orElseThrow(() -> new UsernameNotFoundException(
                "User of username (" + username + ") not found"));
    }

}
