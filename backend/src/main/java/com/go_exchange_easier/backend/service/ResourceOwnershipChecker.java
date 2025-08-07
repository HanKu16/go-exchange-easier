package com.go_exchange_easier.backend.service;

import com.go_exchange_easier.backend.model.Exchange;

public interface ResourceOwnershipChecker {

    boolean isOwner(Exchange exchange);

}
