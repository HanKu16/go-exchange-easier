package com.go_exchange_easier.backend.core.domain.location.city;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CitiesRepository extends JpaRepository<City, Integer>,
        JpaSpecificationExecutor<City> {

    @Override
    @NonNull
    @EntityGraph(attributePaths = {"country"})
    List<City> findAll(@Nullable Specification<City> specification);

}
