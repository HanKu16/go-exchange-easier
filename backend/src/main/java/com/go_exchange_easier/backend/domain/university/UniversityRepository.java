package com.go_exchange_easier.backend.domain.university;

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
           	(uf.follower_id IS NOT NULL) AS is_followed
        FROM core.universities u
        JOIN core.cities ci ON ci.city_id = u.city_id
        JOIN core.countries co ON co.country_id = ci.country_id
        LEFT JOIN core.university_follows uf ON
            uf.university_id = u.university_id AND
          	uf.follower_id = :currentUserId
        WHERE u.university_id = :universityId AND u.deleted_at IS NULL
       """, nativeQuery = true)
    List<Object[]> findProfileById(@Param("universityId") int universityId,
            @Param("currentUserId") int currentUserId);

    @Query("""
        SELECT u FROM University u
        JOIN FETCH u.city c
        JOIN FETCH c.country
        WHERE c.id = :cityId
        """)
    List<University> findByCityId(@Param("cityId") int cityId);

    @Override
    @NonNull
    @EntityGraph(attributePaths = {"city", "city.country"})
    Page<University> findAll(Specification<University> specification,
            @NonNull Pageable pageable);

}
