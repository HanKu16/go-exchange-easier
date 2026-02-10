package com.go_exchange_easier.backend.domain.follow.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFollowRepository extends JpaRepository<UserFollow, UserFollowId> {

    @Modifying
    @Query("DELETE FROM UserFollow f WHERE " +
            "f.follower.id = :followerId AND f.followee.id = :followeeId")
    int deleteByFollowerIdAndFolloweeId(@Param("followerId") int followerId,
            @Param("followeeId") int followeeId);

    @Modifying
    @Query(value = "INSERT INTO core.user_follows (follower_id, followee_id)" +
            "VALUES (:followerId, :followeeId)", nativeQuery = true)
    int insertByNativeQuery(@Param("followerId") int followerId,
            @Param("followeeId") int followeeId);

    @Query(value = "SELECT COUNT(*) FROM core.user_follows WHERE " +
            "follower_id = :followerId AND followee_id = :followeeId",
            nativeQuery = true)
    int countByFollowerIdAndFolloweeId(@Param("followerId") int followerId,
            @Param("followeeId") int followeeId);

}
