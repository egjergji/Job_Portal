package com.example.jobportalbackend.repository;

import com.example.jobportalbackend.model.entity.Employer;
import com.example.jobportalbackend.model.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {

    Page<Job> findByEmployer(Employer employer, Pageable pageable);

    Page<Job> findByEmployerAndTitleContainingIgnoreCase(Employer employer, String title, Pageable pageable);
}
