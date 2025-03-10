package com.example.jobportalbackend.repository;

import com.example.jobportalbackend.model.entity.Employer;
import com.example.jobportalbackend.model.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JobRepository extends JpaRepository<Job, Long> {
    Page<Job> findByEmployer(Employer employer, Pageable pageable);
    Page<Job> findByEmployerAndTitleContainingIgnoreCaseOrLocationContainingIgnoreCase(Employer employer, String title, String location, Pageable pageable);
    @Query("SELECT j FROM Job j WHERE " +
            "(:title IS NULL OR LOWER(j.title) LIKE LOWER(CONCAT('%', :title, '%'))) " +
            "OR (:location IS NULL OR LOWER(j.location) LIKE LOWER(CONCAT('%', :location, '%'))) " +
            "OR (:employerId IS NULL OR j.employer.id = :employerId)")
    Page<Job> findJobsWithFilters(String title, String location, Long employerId, Pageable pageable);
}
