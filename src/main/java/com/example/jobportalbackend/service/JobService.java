package com.example.jobportalbackend.service;

import com.example.jobportalbackend.model.entity.Job;
import com.example.jobportalbackend.model.entity.Employer;
import com.example.jobportalbackend.repository.JobRepository;
import com.example.jobportalbackend.repository.EmployerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private final EmployerRepository employerRepository;

    public JobService(JobRepository jobRepository, EmployerRepository employerRepository) {
        this.jobRepository = jobRepository;
        this.employerRepository = employerRepository;
    }

    public Job createJob(Long employerId, Job job) {
        Employer employer = employerRepository.findById(employerId)
                .orElseThrow(() -> new RuntimeException("Employer not found"));
        job.setEmployer(employer);
        return jobRepository.save(job);
    }

    public Page<Job> getJobsByEmployer(Long employerId, String title, Pageable pageable) {
        Employer employer = employerRepository.findById(employerId)
                .orElseThrow(() -> new RuntimeException("Employer not found"));

        Pageable fixedPageable = PageRequest.of(pageable.getPageNumber(), 10);

        if (title != null && !title.isEmpty()) {
            return jobRepository.findByEmployerAndTitleContainingIgnoreCase(employer, title, pageable);
        }
        return jobRepository.findByEmployer(employer, fixedPageable);
    }
}
