package com.customgrant.custom_grant.repositories;

import com.customgrant.custom_grant.entities.Role;
import com.customgrant.custom_grant.entities.User;
import com.customgrant.custom_grant.entities.enums.RoleType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    Optional<Role> findByAuthority(RoleType authority);
}

