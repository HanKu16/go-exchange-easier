package com.go_exchange_easier.backend.repository;

import com.go_exchange_easier.backend.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends
        JpaRepository<User, Integer>,
        JpaSpecificationExecutor<User> {

    @Query(value = "SELECT " +
            "us.user_id, " +
            "us.nick, " +
            "ud.text_content, " +
            "un.university_id, " +
            "un.original_name, " +
            "un.english_name, " +
            "co.country_id, " +
            "co.english_name, " +
            "ust.user_status_id, " +
            "ust.name, " +
            "(uf.follower_id IS NOT NULL) AS is_followed " +
            "FROM users us " +
            "LEFT JOIN user_descriptions ud ON ud.user_description_id = us.user_description_id " +
            "LEFT JOIN universities un ON un.university_id = us.home_university_id AND un.deleted_at IS NULL " +
            "LEFT JOIN countries co ON co.country_id = us.country_of_origin_id " +
            "LEFT JOIN user_statuses ust ON ust.user_status_id = us.user_status_id " +
            "LEFT JOIN user_follows uf ON uf.follower_id = :currentUserId " +
            "AND uf.followee_id = :userId " +
            "WHERE us.user_id = :userId AND us.deleted_at IS NULL " +
            "LIMIT 1", nativeQuery = true)
     List<Object[]> findProfileById(@Param("userId") int userId,
             @Param("currentUserId") int currentUserId);

    @Modifying
    @Query("UPDATE User u SET u.status.id = :statusId " +
            "WHERE u.id = :userId")
    int updateStatus(@Param("userId") int userId, @Param("statusId") Short statusId);

    @Modifying
    @Query("UPDATE User u SET u.homeUniversity.id = :homeUniversityId " +
            "WHERE u.id = :userId")
    int updateHomeUniversity(@Param("userId") int userId,
            @Param("homeUniversityId") short homeUniversityId);

    @Modifying
    @Query("UPDATE User u SET u.countryOfOrigin.id = :countryId " +
            "WHERE u.id = :userId")
    int assignCountryOfOrigin(@Param("userId") int userId,
            @Param("countryId") Short countryOfOriginId);

    @Query("SELECT u FROM User u WHERE u.nick = :nick")
    @EntityGraph(attributePaths = {"countryOfOrigin", "homeUniversity"})
    Page<User> findByNick(@Param("nick") String nick , Pageable pageable);

}
