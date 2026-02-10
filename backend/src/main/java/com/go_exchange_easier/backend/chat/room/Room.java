package com.go_exchange_easier.backend.chat.room;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "rooms", schema = "chat")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Room {

    @Id
    @Column(name = "room_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(name = "last_message_at")
    private OffsetDateTime lastMessageAt;

    @Column(name = "last_message_text_content")
    private String lastMessageTextContent;

    @Column(name = "last_message_author_id")
    private Integer lastMessageAuthorId;

    @Column(name = "last_message_author_nick")
    private String lastMessageAuthorNick;

    @Column(name = "last_message_author_avatar_key")
    private String lastMessageAuthorAvatarKey;

    @OneToMany(mappedBy = "room")
    private Set<UserInRoom> users = new HashSet<>();

}
