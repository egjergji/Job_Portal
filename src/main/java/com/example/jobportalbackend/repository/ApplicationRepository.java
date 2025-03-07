package com.example.jobportalbackend.repository;

import com.example.jobportalbackend.model.entity.Application;
import com.example.jobportalbackend.model.entity.Job;
import com.example.jobportalbackend.model.entity.JobSeeker;
import com.example.jobportalbackend.model.enums.ApplicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    // ✅ Get all applications for a job (employer view)
    Page<Application> findByJob(Job job, Pageable pageable);

    // ✅ Get all applications for a job seeker (seeker view)
    Page<Application> findByJobSeeker(JobSeeker jobSeeker, Pageable pageable);

    // ✅ Get applications filtered by status
    List<Application> findByJobAndStatus(Job job, ApplicationStatus status);
}
