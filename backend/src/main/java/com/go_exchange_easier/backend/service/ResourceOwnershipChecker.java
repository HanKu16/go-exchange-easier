package com.go_exchange_easier.backend.service;

import com.go_exchange_easier.backend.domain.exchange.Exchange;
import com.go_exchange_easier.backend.domain.university.UniversityReview;

public interface ResourceOwnershipChecker {

    boolean isOwner(Exchange exchange);
    boolean isOwner(UniversityReview review);

}
