package com.go_exchange_easier.backend.core.domain.follow.university;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;
import lombok.*;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UniversityFollowId implements Serializable {

    private UUID follower;
    private Short university;

}
