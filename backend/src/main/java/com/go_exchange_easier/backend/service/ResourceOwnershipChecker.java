package com.go_exchange_easier.backend.service;

import com.go_exchange_easier.backend.model.Exchange;
import com.go_exchange_easier.backend.model.UniversityReview;

public interface ResourceOwnershipChecker {

    boolean isOwner(Exchange exchange);
    boolean isOwner(UniversityReview review);

}
