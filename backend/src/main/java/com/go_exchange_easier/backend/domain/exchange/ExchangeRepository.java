package com.go_exchange_easier.backend.domain.exchange;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRepository extends
        JpaRepository<Exchange, Integer>,
        JpaSpecificationExecutor<Exchange> {

    @Override
    @NonNull
    @EntityGraph(attributePaths = {"user", "fieldOfStudy", "university", "university.city",
        "university.city.country"})
    Page<Exchange> findAll(@Nullable Specification<Exchange> specification,
            @NonNull Pageable pageable);

}
