package com.go_exchange_easier.backend.domain.auth.entity;

import com.go_exchange_easier.backend.domain.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "user_credentials")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserCredentials implements UserDetails {

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles
                .stream()
                .map(r -> new SimpleGrantedAuthority(r.toString()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
