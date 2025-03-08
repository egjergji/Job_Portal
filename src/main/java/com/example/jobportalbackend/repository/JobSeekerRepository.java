package com.example.jobportalbackend.repository;

import com.example.jobportalbackend.model.entity.JobSeeker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobSeekerRepository extends JpaRepository<JobSeeker, Long> {


    Optional<JobSeeker> findByUsername(String username);
}
