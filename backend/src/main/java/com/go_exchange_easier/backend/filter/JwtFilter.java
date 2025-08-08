package com.go_exchange_easier.backend.filter;

import com.go_exchange_easier.backend.exception.MissingJwtClaimException;
import com.go_exchange_easier.backend.security.jwt.JwtClaimsExtractor;
import com.go_exchange_easier.backend.security.jwt.JwtTokenValidator;
import com.go_exchange_easier.backend.service.impl.UserCredentialsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
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
    private final ApplicationContext applicationContext;

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
            UserDetails userDetails = loadUser(username);
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

    private UserDetails loadUser(String username) {
        return applicationContext
                .getBean(UserCredentialsServiceImpl.class)
                .loadUserByUsername(username);
    }

}
