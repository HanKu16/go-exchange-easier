package com.go_exchange_easier.backend.repository;

import com.go_exchange_easier.backend.model.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UniversityRepository extends
        JpaRepository<University, Short>,
        JpaSpecificationExecutor<University> {

    @Query("SELECT u FROM University u " +
            "JOIN FETCH u.city c " +
            "JOIN FETCH c.country " +
            "WHERE c.country.id = :countryId")
    List<University> findByCountryId(short countryId);

    @Query(value = """
        SELECT\s
            u.university_id,
            u.original_name,
           	u.english_name,
           	u.link_to_website,
           	ci.english_name,
           	co.english_name,
           	(uf.follower_id IS NOT NULL) AS is_followed
        FROM universities u
        JOIN cities ci ON ci.city_id = u.city_id
        JOIN countries co ON co.country_id = ci.country_id
        LEFT JOIN university_follows uf ON
            uf.university_id = u.university_id AND
          	uf.follower_id = :currentUserId
        WHERE u.university_id = :universityId AND u.deleted_at IS NULL
       """, nativeQuery = true)
    List<Object[]> findProfileById(@Param("universityId") int universityId,
            @Param("currentUserId") int currentUserId);

    List<University> findByOriginalName(String originalName);
    List<University> findByEnglishName(String englishName);

    @Query("""
        SELECT u FROM University u
        JOIN FETCH u.city c
        JOIN FETCH c.country
        WHERE c.id = :cityId
        """)
    List<University> findByCityId(@Param("cityId") int cityId);

}
