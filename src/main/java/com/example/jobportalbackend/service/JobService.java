package com.example.jobportalbackend.service;

import com.example.jobportalbackend.model.entity.Job;
import com.example.jobportalbackend.model.entity.Employer;
import com.example.jobportalbackend.repository.JobRepository;
import com.example.jobportalbackend.repository.EmployerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }
}
