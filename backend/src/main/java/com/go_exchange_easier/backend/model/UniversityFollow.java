package com.go_exchange_easier.backend.model;

import com.go_exchange_easier.backend.model.keys.UniversityFollowId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "university_follows")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@IdClass(UniversityFollowId.class)
public class UniversityFollow {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id")
    private User follower;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id")
    private University university;

}
