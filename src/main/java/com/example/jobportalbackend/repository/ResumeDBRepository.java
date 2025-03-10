package com.example.jobportalbackend.repository;

import com.example.jobportalbackend.model.entity.ResumeDB;
import com.example.jobportalbackend.model.entity.JobSeeker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResumeDBRepository extends JpaRepository<ResumeDB, Long> {
    Optional<ResumeDB> findByJobSeeker(JobSeeker jobSeeker);
}
