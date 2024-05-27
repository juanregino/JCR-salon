package com.jcr.salon.domain.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jcr.salon.domain.entities.User;

public interface UserRepository extends JpaRepository<User , UUID> {
  public Optional<User> findByEmail(String email);
}
