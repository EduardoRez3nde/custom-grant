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

    private Role(final Builder builder) {
        this.id = builder.id;
        this.authority = builder.authority;
    }

    public static class Builder {

        private UUID id;
        private RoleType authority;

        public Builder id(final UUID id) {
            this.id = id;
            return this;
        }

        public Builder authority(final RoleType authority) {
            this.authority = authority;
            return this;
        }

        public Role build() {
            return new Role(this);
        }
    }

    public static Builder builder() {
        return new Builder();
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

    public RoleType getRole() {
        return authority;
    }

    public void setAuthority(final RoleType authority) {
        this.authority = authority;
    }
}
