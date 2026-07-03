package com.go_exchange_easier.backend.core.domain.auth.impl;

import com.go_exchange_easier.backend.core.domain.auth.UserCredentialsRepository;
import com.go_exchange_easier.backend.core.domain.auth.dto.AuthenticatedUser;
import com.go_exchange_easier.backend.core.domain.auth.entity.Principal;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserCredentialsRepository userCredentialsRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Principal principal = userCredentialsRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User of username " + username + " was not found."));
        UUID principalId = principal.getUser()
                .getId();
        boolean enabled = principal.getUser()
                .getDeletedAt() == null;
        return new AuthenticatedUser(
                principalId,
                principal.getUsername(),
                principal.getPassword(),
                enabled,
                principal.getUser()
                        .getNick(),
                principal.getUser()
                        .getAvatarKey(),
                principal.getRoles()
        );
    }

}
