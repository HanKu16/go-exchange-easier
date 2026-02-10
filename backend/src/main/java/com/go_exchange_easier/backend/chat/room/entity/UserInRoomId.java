package com.go_exchange_easier.backend.chat.room.entity;

import jakarta.persistence.Embeddable;
import lombok.*;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserInRoomId implements Serializable {

    private Room room;
    private int userId;

}
