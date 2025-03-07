package com.example.jobportalbackend.repository;

import com.example.jobportalbackend.model.entity.User;
import com.example.jobportalbackend.model.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // ✅ Get all users with pagination and filtering by role
    Page<User> findByRole(Role role, Pageable pageable);

    // ✅ Find user by username (for authentication)
    Optional<User> findByUsername(String username);
}
