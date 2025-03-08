package com.example.jobportalbackend.repository;

import com.example.jobportalbackend.model.entity.JobSeeker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobSeekerRepository extends JpaRepository<JobSeeker, Long> {

    // ✅ Find a job seeker by username (useful for authentication or user lookup)
    Optional<JobSeeker> findByUsername(String username);
}
