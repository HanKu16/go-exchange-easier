package com.go_exchange_easier.backend.service.impl;

import com.go_exchange_easier.backend.exception.InvalidPrincipalTypeException;
import com.go_exchange_easier.backend.model.Exchange;
import com.go_exchange_easier.backend.model.UniversityReview;
import com.go_exchange_easier.backend.model.UserCredentials;
import com.go_exchange_easier.backend.service.ResourceOwnershipChecker;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import java.util.Objects;

@Component
public class ResourceOwnershipCheckerImpl implements ResourceOwnershipChecker {

    @Override
    public boolean isOwner(Exchange exchange) {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        if (authentication.getPrincipal() instanceof UserCredentials userCredentials) {
            return Objects.equals(exchange.getUser().getId(),
                    userCredentials.getUser().getId());
        }
        throw new InvalidPrincipalTypeException("Principal was expected to be of " +
                "type UserCredentials but was not.");
    }

    @Override
    public boolean isOwner(UniversityReview review) {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        if (authentication.getPrincipal() instanceof UserCredentials userCredentials) {
            return Objects.equals(review.getAuthor().getId(), userCredentials
                    .getUser().getId());
        }
        throw new InvalidPrincipalTypeException("Principal was expected to be of " +
                "type UserCredentials but was not.");
    }

}
