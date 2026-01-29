package com.go_exchange_easier.backend.domain.follow.university;

import jakarta.persistence.Embeddable;
import lombok.*;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UniversityFollowId implements Serializable {

    private Integer follower;
    private Short university;

}
