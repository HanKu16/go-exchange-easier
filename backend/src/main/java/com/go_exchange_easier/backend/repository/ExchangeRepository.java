package com.go_exchange_easier.backend.repository;

import com.go_exchange_easier.backend.model.Exchange;
import com.go_exchange_easier.backend.repository.specification.ExchangeSpecification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ExchangeRepository extends
        JpaRepository<Exchange, Integer>,
        JpaSpecificationExecutor<Exchange> {

//    @Query(value = "SELECT " +
//            "ex.exchange_id, " +
//            "ex.started_at, " +
//            "ex.end_at, " +
//            "un.university_id, " +
//            "un.original_name, " +
//            "un.english_name, " +
//            "ci.city_id, " +
//            "ci.english_name, " +
//            "co.country_id, " +
//            "co.english_name, " +
//            "um.university_major_id, " +
//            "um.name " +
//            "FROM exchanges ex " +
//            "JOIN users us ON us.user_id = ex.user_id " +
//            "JOIN universities un ON un.university_id = ex.university_id " +
//            "JOIN university_majors um ON um.university_major_id = ex.university_major_id " +
//            "JOIN cities ci ON ci.city_id = un.city_id " +
//            "JOIN countries co ON co.country_id = ci.country_id " +
//            "WHERE us.user_id = :userId AND us.deleted_at IS NULL", nativeQuery = true)
    @Query(value = """
            SELECT
                ex.exchange_id,
                ex.started_at,
                ex.end_at,
                un.university_id,
                un.original_name,
                un.english_name,
                ci.city_id,
                ci.english_name,
                co.country_id,
                co.english_name,
                um.university_major_id,
                um.name,
                us.user_id,
                us.nick
            FROM exchanges ex
            JOIN users us ON us.user_id = ex.user_id
            JOIN universities un ON un.university_id = ex.university_id
            JOIN university_majors um ON um.university_major_id = ex.university_major_id
            JOIN cities ci ON ci.city_id = un.city_id
            JOIN countries co ON co.country_id = ci.country_id
            WHERE us.user_id = :userId AND us.deleted_at IS NULL;
        """, nativeQuery = true)
    List<Object[]> findByUserId(@Param("userId") int userId);

}
