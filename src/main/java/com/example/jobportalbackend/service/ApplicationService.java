package com.example.jobportalbackend.service;

import com.example.jobportalbackend.model.dto.ApplicationDTO;
import com.example.jobportalbackend.model.entity.Application;
import com.example.jobportalbackend.model.entity.Job;
import com.example.jobportalbackend.model.enums.ApplicationStatus;
import com.example.jobportalbackend.repository.ApplicationRepository;
import com.example.jobportalbackend.repository.JobRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;

    public ApplicationService(ApplicationRepository applicationRepository, JobRepository jobRepository) {
        this.applicationRepository = applicationRepository;
        this.jobRepository = jobRepository;
    }

    public Page<ApplicationDTO> getApplicationsForJob(Long jobId, ApplicationStatus status, Pageable pageable) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));


        Page<Application> applicationPage = (status != null)
                ? applicationRepository.findByJobAndStatus(job, status, pageable)
                : applicationRepository.findByJob(job, pageable);


        List<ApplicationDTO> dtos = applicationPage.getContent().stream()
                .map(app -> new ApplicationDTO(app.getId(), app.getJob(), app.getJobSeeker(), app.getStatus()))
                .collect(Collectors.toList());

        return new PageImpl<>(dtos, pageable, applicationPage.getTotalElements());
    }

    public void updateApplicationStatus(Long applicationId, Long employerId, Long jobId, ApplicationStatus status) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        if (!application.getJob().getEmployer().getId().equals(employerId)) {
            throw new RuntimeException("Unauthorized: Only the employer who posted this job can update application status.");
        }

        application.setStatus(status);
        applicationRepository.save(application);
    }
}
