package com.go_exchange_easier.backend.infrastracture.security.jwt;

import com.go_exchange_easier.backend.domain.auth.Role;
import com.go_exchange_easier.backend.domain.user.User;
import com.go_exchange_easier.backend.domain.auth.UserCredentials;
import com.go_exchange_easier.backend.domain.auth.RoleRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
    private final JwtClaimsExtractor jwtClaimsExtractor;
    private final JwtTokenValidator jwtTokenValidator;
    private final RoleRepository roleRepository;

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
        String accessToken = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("accessToken".equals(cookie.getName())) {
                    accessToken = cookie.getValue();
                }
            }
        }
        if ((accessToken != null) && jwtTokenValidator.validate(accessToken)) {
            int userId = jwtClaimsExtractor.extractUserId(accessToken);
            String username = jwtClaimsExtractor.extractUsername(accessToken);
            List<String> authorities = jwtClaimsExtractor.extractRoles(accessToken);
            UserCredentials userDetails = new UserCredentials();
            userDetails.setUser(buildUserProxy(userId));
            userDetails.setUsername(username);
            Set<Role> roles = authorities.stream()
                    .map(roleRepository::findByName)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());
            userDetails.setRoles(roles);
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

    private User buildUserProxy(int userId) {
        User userProxy = new User();
        userProxy.setId(userId);
        return userProxy;
    }

}
