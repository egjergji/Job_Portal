package com.example.jobportalbackend.repository;

import com.example.jobportalbackend.model.entity.Employer;
import com.example.jobportalbackend.model.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {

    // ✅ Get all jobs posted by an employer
    Page<Job> findByEmployer(Employer employer, Pageable pageable);

    // ✅ Get all jobs with filtering by title/location
    Page<Job> findByTitleContainingIgnoreCaseOrEmployer_CompanyNameContainingIgnoreCase(
            String title, String companyName, Pageable pageable);
}
