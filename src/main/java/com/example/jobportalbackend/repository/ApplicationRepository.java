package com.example.jobportalbackend.repository;

import com.example.jobportalbackend.model.entity.Application;
import com.example.jobportalbackend.model.entity.Job;
import com.example.jobportalbackend.model.enums.ApplicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    @Query("SELECT a FROM Application a WHERE a.jobSeeker.id = :jobSeekerId " +
            "AND (:jobTitle IS NULL OR LOWER(a.job.title) LIKE LOWER(CONCAT('%', :jobTitle, '%'))) " +
            "AND (:status IS NULL OR a.status = :status)")
    Page<Application> findApplicationsByJobSeeker(Long jobSeekerId, String jobTitle, String status, Pageable pageable);

    Page<Application> findByJob(Job job, Pageable pageable);

    Page<Application> findByJobAndStatus(Job job, ApplicationStatus status, Pageable pageable);
}
