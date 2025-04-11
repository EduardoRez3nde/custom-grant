package com.customgrant.custom_grant.repositories;

import com.customgrant.custom_grant.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {  }

