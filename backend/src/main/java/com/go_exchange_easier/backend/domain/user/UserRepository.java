package com.go_exchange_easier.backend.domain.user;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

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
            "(uf.follower_id IS NOT NULL) AS is_followed, " +
            "us.avatar_key " +
            "FROM core.users us " +
            "LEFT JOIN core.user_descriptions ud ON ud.user_id = us.user_id " +
            "LEFT JOIN core.universities un ON un.university_id = us.home_university_id AND un.deleted_at IS NULL " +
            "LEFT JOIN core.countries co ON co.country_id = us.country_of_origin_id " +
            "LEFT JOIN core.user_statuses ust ON ust.user_status_id = us.user_status_id " +
            "LEFT JOIN core.user_follows uf ON uf.follower_id = :currentUserId " +
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

    @EntityGraph(attributePaths = {"userFollowsSent", "userFollowsSent.followee"},
            type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT u FROM User u WHERE u.id = :userId")
    Optional<User> findWithFollowees(@Param("userId") int userId);

    @EntityGraph(attributePaths = {"universityFollowsSent",
            "universityFollowsSent.university"},
            type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT u FROM User u WHERE u.id = :userId")
    Optional<User> findWithFollowedUniversities(@Param("userId") int userId);

}
