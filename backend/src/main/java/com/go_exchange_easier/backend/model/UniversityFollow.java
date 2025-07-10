package com.go_exchange_easier.backend.model;

import com.go_exchange_easier.backend.model.keys.UniversityFollowId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "university_follows")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UniversityFollowId.class)
public class UniversityFollow {

    @Id
    @ManyToOne
    @JoinColumn(name = "follower_id")
    private User follower;

    @Id
    @ManyToOne
    @JoinColumn(name = "university_id")
    private University university;

}
