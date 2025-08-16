package com.go_exchange_easier.backend.repository;

import com.go_exchange_easier.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Modifying
    @Query("UPDATE User u SET u.status.id = :statusId WHERE u.id = :userId")
    int updateStatus(@Param("userId") int userId, @Param("statusId") short statusId);

    @Modifying
    @Query("UPDATE User u SET u.homeUniversity.id = :homeUniversityId " +
            "WHERE u.id = :userId")
    int updateHomeUniversity(@Param("userId") int userId,
            @Param("homeUniversityId") short homeUniversityId);

}
