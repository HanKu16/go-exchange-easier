package com.go_exchange_easier.backend.repository;

import com.go_exchange_easier.backend.model.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UniversityRepository extends JpaRepository<University, Short> {

    @Query("SELECT u FROM University u " +
            "WHERE u.city.country.id = :countryId")
    List<University> findByCountryId(short countryId);

}
