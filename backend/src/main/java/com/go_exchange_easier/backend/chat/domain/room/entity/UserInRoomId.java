package com.go_exchange_easier.backend.chat.domain.room.entity;

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
public class UserInRoomId implements Serializable {

    private Room room;
    private UUID userId;

}
