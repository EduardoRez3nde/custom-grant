package com.customgrant.custom_grant.repositories;

import com.customgrant.custom_grant.entities.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;


public interface UserRepository extends JpaRepository<User, UUID> {

    @EntityGraph(attributePaths = "tb_roles")
    Optional<UserDetails> findByUsername(final String username);
}

