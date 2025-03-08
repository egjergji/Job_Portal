package com.example.jobportalbackend.repository;

import com.example.jobportalbackend.model.entity.Application;
import com.example.jobportalbackend.model.entity.Job;
import com.example.jobportalbackend.model.enums.ApplicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    // ✅ Get all applications for a job (paginated)
    Page<Application> findByJob(Job job, Pageable pageable);

    // ✅ Get applications filtered by status (paginated)
    Page<Application> findByJobAndStatus(Job job, ApplicationStatus status, Pageable pageable);
}
