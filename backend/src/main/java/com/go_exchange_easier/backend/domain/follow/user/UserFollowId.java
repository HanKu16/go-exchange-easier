package com.go_exchange_easier.backend.domain.follow.user;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.*;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserFollowId implements Serializable {

    private Integer follower;
    private Integer followee;

}
