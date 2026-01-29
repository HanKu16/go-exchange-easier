package com.go_exchange_easier.backend.domain.follow.university;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UniversityFollowRepository extends
        JpaRepository<UniversityFollow, UniversityFollowId> {

    @Modifying
    @Query("DELETE UniversityFollow f WHERE f.follower.id = :followerId " +
            "AND f.university.id = :universityId")
    int deleteByUniversityIdAndFollowerId(@Param("universityId") short universityId,
            @Param("followerId") int followerId);

    @Modifying
    @Query(value = "INSERT INTO university_follows (follower_id, university_id)" +
            "VALUES (:followerId, :universityId)", nativeQuery = true)
    int insertByNativeQuery(@Param("universityId") short universityId,
            @Param("followerId") int followerId);

    @Query(value = "SELECT COUNT(*) FROM university_follows " +
            "WHERE university_id = :universityId AND follower_id = :followerId",
            nativeQuery = true)
    int countByUniversityIdAndFollowerIdNativeQuery(
            @Param("universityId") short universityId,
            @Param("followerId") int followerId);

}
