package com.go_exchange_easier.backend.infrastracture.security.jwt;

import com.go_exchange_easier.backend.domain.auth.dto.AuthenticatedUser;
import com.go_exchange_easier.backend.domain.auth.entity.Role;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.NonNull;
import org.springframework.web.servlet.HandlerExceptionResolver;

/**
 * {@code JwtFilter} is a custom Spring Security filter responsible for authenticating
 * users based on JSON Web Tokens (JWTs) provided in the Authorization header.
 *
 * <p>This filter extends {@link OncePerRequestFilter} to ensure it's executed only once
 * per request, providing a centralized point for JWT-based authentication within the
 * Spring Security filter chain.</p>
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtClaimsExtractor jwtClaimsExtractor;
    private final JwtTokenValidator jwtTokenValidator;
    private final HandlerExceptionResolver resolver;

    public JwtFilter(
            JwtClaimsExtractor jwtClaimsExtractor,
            JwtTokenValidator jwtTokenValidator,
            @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.jwtClaimsExtractor = jwtClaimsExtractor;
        this.jwtTokenValidator = jwtTokenValidator;
        this.resolver = resolver;
    }

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
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            resolver.resolveException(request, response, null, e);
            return;
        }
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
            Set<Role> roles = jwtClaimsExtractor.extractRoles(accessToken)
                    .stream().map(Role::valueOf).collect(Collectors.toSet());
            AuthenticatedUser authenticatedUser = new AuthenticatedUser(
                    userId, username, null, true, roles);
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            authenticatedUser, null, authenticatedUser.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource()
                    .buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
    }

    private boolean isUserAlreadyAuthenticated() {
        return SecurityContextHolder.getContext()
                .getAuthentication() != null;
    }

}
