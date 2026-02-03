package com.go_exchange_easier.backend.domain.auth.entity;

import com.go_exchange_easier.backend.domain.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_credentials")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserCredentials {

    @Id
    @Column(name = "user_credential_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = "username")
    @EqualsAndHashCode.Include
    private String username;

    @Column(name = "password")
    private String password;

    @OneToOne(mappedBy = "credentials")
    private User user;

    @Column(name = "role", columnDefinition = "role")
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_credential_id")
    )
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private Set<Role> roles = new HashSet<>();

}
