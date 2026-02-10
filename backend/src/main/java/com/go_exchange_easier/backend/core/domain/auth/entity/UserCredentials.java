package com.go_exchange_easier.backend.core.domain.auth.entity;

import com.go_exchange_easier.backend.core.domain.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_credentials", schema = "core")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserCredentials {

    @Id
    @Column(name = "user_id")
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = "username")
    @EqualsAndHashCode.Include
    private String username;

    @Column(name = "password")
    private String password;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "role", columnDefinition = "role")
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "user_roles",
            schema = "core",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private Set<Role> roles = new HashSet<>();

}
