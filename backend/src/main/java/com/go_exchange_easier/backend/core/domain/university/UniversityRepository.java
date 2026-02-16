package com.go_exchange_easier.backend.core.domain.university;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UniversityRepository extends
        JpaRepository<University, Short>,
        JpaSpecificationExecutor<University> {

    @Query(value = """
        SELECT
            u.university_id,
            u.original_name,
           	u.english_name,
           	u.link_to_website,
           	ci.english_name,
           	co.english_name,
           	ci.city_id,
           	co.country_id,
           	co.flag_key
        FROM core.universities u
        JOIN core.cities ci ON ci.city_id = u.city_id
        JOIN core.countries co ON co.country_id = ci.country_id
        WHERE u.university_id = :universityId AND u.deleted_at IS NULL
       """, nativeQuery = true)
    List<Object[]> findProfileById(@Param("universityId") int universityId);

    @Override
    @NonNull
    @EntityGraph(attributePaths = {"city", "city.country"})
    Page<University> findAll(Specification<University> specification,
            @NonNull Pageable pageable);

    @EntityGraph(attributePaths = {"city, city.country"})
    Optional<University> findById(short id);

}
