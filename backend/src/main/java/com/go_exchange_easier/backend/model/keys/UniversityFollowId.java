package com.go_exchange_easier.backend.model.keys;

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
