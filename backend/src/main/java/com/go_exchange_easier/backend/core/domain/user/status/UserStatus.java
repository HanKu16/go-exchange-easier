package com.go_exchange_easier.backend.core.domain.user.status;

import com.go_exchange_easier.backend.core.domain.user.User;
import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_statuses", schema = "core")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserStatus {

    @Id
    @Column(name = "user_status_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Short id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "status")
    private Set<User> usersWithStatus = new HashSet<>();

}
