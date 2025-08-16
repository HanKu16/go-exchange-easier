package com.go_exchange_easier.backend.filter;

import com.go_exchange_easier.backend.dto.auth.UserCredentialsDto;
import com.go_exchange_easier.backend.exception.MissingJwtClaimException;
import com.go_exchange_easier.backend.model.Role;
import com.go_exchange_easier.backend.model.User;
import com.go_exchange_easier.backend.model.UserCredentials;
import com.go_exchange_easier.backend.repository.UserCredentialsRepository;
import com.go_exchange_easier.backend.security.jwt.JwtClaimsExtractor;
import com.go_exchange_easier.backend.security.jwt.JwtTokenValidator;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.stream.Collectors;
import lombok.NonNull;

/**
 * {@code JwtFilter} is a custom Spring Security filter responsible for authenticating
 * users based on JSON Web Tokens (JWTs) provided in the Authorization header.
 *
 * <p>This filter extends {@link OncePerRequestFilter} to ensure it's executed only once
 * per request, providing a centralized point for JWT-based authentication within the
 * Spring Security filter chain.</p>
 */
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private static final Logger logger = LogManager.getLogger(JwtFilter.class);
    private final UserCredentialsRepository credentialsRepository;
    private final JwtClaimsExtractor jwtClaimsExtractor;
    private final JwtTokenValidator jwtTokenValidator;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        if (isUserAlreadyAuthenticated()) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            tryDoFilterInternal(request);
        } catch (MissingJwtClaimException e) {
            logger.error("Missing username claim in the token: {}", e.getMessage());
        } catch (UsernameNotFoundException e) {
            logger.error("User of given username does not exist: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("An error occurred: {}", e.getMessage());
        }
        filterChain.doFilter(request, response);
    }

    private void tryDoFilterInternal(HttpServletRequest request) {
        String token = parseToken(request);
        if ((token != null) && jwtTokenValidator.validate(token)) {
            String username = jwtClaimsExtractor.extractUsername(token);
            UserCredentialsDto credentialsDto = credentialsRepository
                    .findDtoByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException(
                            "User of username " + username + " was not found."));
            UserDetails userDetails = mapDtoToEntity(credentialsDto, token);
            if (!userDetails.isEnabled() || !userDetails.isAccountNonLocked())  {
                return;
            }
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource()
                    .buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
    }

    private boolean isUserAlreadyAuthenticated() {
        return SecurityContextHolder.getContext()
                .getAuthentication() != null;
    }

    private String parseToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        return isAuthHeaderValid(authHeader) ?
                authHeader.substring(7) :
                null;
    }

    private boolean isAuthHeaderValid(String authHeader) {
        return (authHeader != null) && (authHeader.startsWith("Bearer "));
    }

    private UserCredentials mapDtoToEntity(UserCredentialsDto credentialsDto,
            String token) {
        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setId(credentialsDto.id());
        userCredentials.setUsername(credentialsDto.username());
        userCredentials.setPassword(credentialsDto.password());
        userCredentials.setEnabled(credentialsDto.isEnabled());
        User userProxy = buildUserProxy(token);
        userCredentials.setUser(userProxy);
        userCredentials.setRoles(jwtClaimsExtractor.extractRoles(token)
                .stream()
                .map(r -> {
                    Role role = new Role();
                    role.setName(r);
                    return role;
                })
                .collect(Collectors.toSet()));
        return userCredentials;
    }

    private User buildUserProxy(String token) {
        Integer userId = jwtClaimsExtractor.extractUserId(token);
        User userProxy = new User();
        userProxy.setId(userId);
        return userProxy;
    }

}
