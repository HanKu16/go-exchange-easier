package com.go_exchange_easier.backend.chat.room;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.OffsetDateTime;

@Entity
@Table(name = "user_in_rooms", schema = "chat")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@IdClass(UserInRoomId.class)
public class UserInRoom {

    @Id
    @ManyToOne
    @JoinColumn(name = "room_id")
    @EqualsAndHashCode.Include
    private Room room;

    @Id
    @Column(name = "user_id")
    @EqualsAndHashCode.Include
    private int userId;

    @Column(name = "last_read_at")
    private OffsetDateTime lastReadAt;

}

