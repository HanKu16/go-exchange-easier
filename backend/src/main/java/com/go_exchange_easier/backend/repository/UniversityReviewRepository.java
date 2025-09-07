package com.go_exchange_easier.backend.repository;

import com.go_exchange_easier.backend.model.UniversityReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UniversityReviewRepository extends
        JpaRepository<UniversityReview, Integer> {

    @Query(value = """
        SELECT\s
            ur.university_review_id,
            us.user_id,
            us.nick,
            un.university_id,
            un.english_name,
            un.original_name,
            ur.star_rating,
            ur.text_content,
            ur.created_at,
            COALESCE(
                json_agg(
                    json_build_object(
                        'typeId', rc.reaction_type_id,
                        'name', rt.name,
                        'count', rc.count,
                        'isSet',  COALESCE((rc.reaction_type_id = urr.reaction_type_id), FALSE)
                    )
                ) FILTER (WHERE rc.reaction_type_id IS NOT NULL),
                '[]'
            ) AS reactions
        FROM university_reviews ur
        LEFT JOIN users us ON us.user_id = ur.author_id
        LEFT JOIN universities un ON un.university_id = ur.university_id
        LEFT JOIN university_reviews_reaction_counts rc ON rc.university_review_id = ur.university_review_id
        LEFT JOIN reaction_types rt ON rt.reaction_type_id = rc.reaction_type_id
        LEFT JOIN university_review_reactions urr ON\s
            urr.university_review_id = ur.university_review_id AND
            urr.author_id = :currentUserId
        WHERE us.user_id = :authorId
        GROUP BY ur.university_review_id, us.user_id, us.nick,
            un.university_id, un.english_name, un.original_name,
            ur.star_rating, ur.text_content, ur.created_at
        ORDER BY ur.created_at DESC
       \s""", nativeQuery = true)
    List<Object[]> findByAuthorId(@Param("authorId") int authorId,
            @Param("currentUserId") int currentUserId);

}


