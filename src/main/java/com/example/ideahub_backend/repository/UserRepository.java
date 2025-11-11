package com.example.ideahub_backend.repository;

import com.example.ideahub_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByUsernameAndIdNot(String username, Long id);
}
