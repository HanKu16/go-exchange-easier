package com.go_exchange_easier.backend.model;

import com.go_exchange_easier.backend.model.keys.UserFollowId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_follows")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@IdClass(UserFollowId.class)
public class UserFollow {

    @Id
    @ManyToOne
    @JoinColumn(name = "follower_id")
    private User follower;

    @Id
    @ManyToOne
    @JoinColumn(name = "followee_id")
    private User followee;

}
