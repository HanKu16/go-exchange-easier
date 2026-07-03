package com.go_exchange_easier.backend.core.domain.auth.entity;

import com.go_exchange_easier.backend.core.domain.user.User;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "principals", schema = "core")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Principal {

    @Id
    @Column(name = "principal_id")
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(name = "username")
    @EqualsAndHashCode.Include
    private String username;

    @Column(name = "password")
    private String password;

    @OneToOne
    @MapsId
    @JoinColumn(name = "principal_id")
    private User user;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "principal_roles", schema = "core", joinColumns = @JoinColumn(name = "principal_id"))
    private Set<Role> roles = new HashSet<>();

}
