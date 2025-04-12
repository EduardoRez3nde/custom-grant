package com.customgrant.custom_grant.entities;

import com.customgrant.custom_grant.entities.enums.RoleType;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.UUID;

@Entity
@Table(name = "tb_roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private RoleType authority;

    public Role() { }

    private Role(final UUID id, final RoleType authority) {
        this.id = id;
        this.authority = authority;
    }

    public static Role from(final UUID id, final RoleType authority) {
        return new Role(id, authority);
    }

    public static Role of(final Role role) {
        return Role.from(role.getId(), role.authority);
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    @Override
    public String getAuthority() {
        return authority.getType();
    }

    public void setAuthority(final RoleType authority) {
        this.authority = authority;
    }
}
