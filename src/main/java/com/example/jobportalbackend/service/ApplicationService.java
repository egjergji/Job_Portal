package com.example.jobportalbackend.service;

import com.example.jobportalbackend.model.dto.ApplicationDTO;
import com.example.jobportalbackend.model.entity.Application;
import com.example.jobportalbackend.model.entity.Job;
import com.example.jobportalbackend.model.entity.JobSeeker;
import com.example.jobportalbackend.model.enums.ApplicationStatus;
import com.example.jobportalbackend.repository.ApplicationRepository;
import com.example.jobportalbackend.repository.JobRepository;
import com.example.jobportalbackend.repository.JobSeekerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final JobSeekerRepository jobSeekerRepository;

    public ApplicationService(ApplicationRepository applicationRepository, JobRepository jobRepository, JobSeekerRepository jobSeekerRepository) {
        this.applicationRepository = applicationRepository;
        this.jobRepository = jobRepository;
        this.jobSeekerRepository = jobSeekerRepository;
    }

    public ApplicationDTO applyForJob(Long jobSeekerId, Long jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        JobSeeker jobSeeker = jobSeekerRepository.findById(jobSeekerId)
                .orElseThrow(() -> new RuntimeException("Job Seeker not found"));

        Application application = new Application(jobSeeker, job, ApplicationStatus.PENDING);
        Application savedApplication = applicationRepository.save(application);

        return new ApplicationDTO(savedApplication.getId(), job.getTitle(), jobSeeker.getUsername(), savedApplication.getStatus().name());
    }

    public List<ApplicationDTO> getApplicationsByJobSeeker(Long jobSeekerId) {
        JobSeeker jobSeeker = jobSeekerRepository.findById(jobSeekerId)
                .orElseThrow(() -> new RuntimeException("Job Seeker not found"));

        return applicationRepository.findByJobSeeker(jobSeeker, null)
                .stream()
                .map(app -> new ApplicationDTO(app.getId(), app.getJob().getTitle(), jobSeeker.getUsername(), app.getStatus().name()))
                .collect(Collectors.toList());
    }

    public List<ApplicationDTO> getApplicationsForJob(Long jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        return applicationRepository.findByJob(job, null)
                .stream()
                .map(app -> new ApplicationDTO(app.getId(), job.getTitle(), app.getJobSeeker().getUsername(), app.getStatus().name()))
                .collect(Collectors.toList());
    }

    public void updateApplicationStatus(Long applicationId, ApplicationStatus status) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        application.setStatus(status);
        applicationRepository.save(application);
    }
}
