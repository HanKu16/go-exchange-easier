package com.go_exchange_easier.backend.chat.room.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Persistable;
import java.time.OffsetDateTime;

@Entity
@Table(name = "user_in_rooms", schema = "chat")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@IdClass(UserInRoomId.class)
public class UserInRoom implements Persistable<UserInRoomId> {

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

    @Transient
    private boolean isNew = true;

    @Override
    public UserInRoomId getId() {
        return new UserInRoomId(room, userId);
    }

    @PostLoad
    @PostPersist
    void markNotNew() {
        this.isNew = false;
    }

}

