package com.example.jobportalbackend.repository;

import com.example.jobportalbackend.model.entity.Employer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployerRepository extends JpaRepository<Employer, Long> {

    // ✅ Find an employer by username (useful for authentication or employer lookup)
    Optional<Employer> findByUsername(String username);
}
