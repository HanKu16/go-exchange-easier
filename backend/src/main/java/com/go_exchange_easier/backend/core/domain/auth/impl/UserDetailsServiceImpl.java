package com.go_exchange_easier.backend.core.domain.auth.impl;

import com.go_exchange_easier.backend.core.domain.auth.PrincipalRepository;
import com.go_exchange_easier.backend.core.domain.auth.dto.AuthenticatedUser;
import com.go_exchange_easier.backend.core.domain.auth.entity.Principal;
import com.go_exchange_easier.backend.core.domain.user.BasicUserProvider;
import com.go_exchange_easier.backend.core.domain.user.dto.BasicUser;
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

    private final PrincipalRepository principalRepository;
    private final BasicUserProvider basicUserProvider;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Principal principal = principalRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User of username " + username + " was not found."));
        UUID principalId = principal.getId();
        BasicUser user = basicUserProvider.getById(principalId);
        boolean enabled = user.deletedAt() == null && !user.isBlocked();
        return new AuthenticatedUser(
                principalId,
                principal.getUsername(),
                principal.getPassword(),
                enabled,
                user.nick(),
                user.avatarKey(),
                principal.getRoles()
        );
    }

}
