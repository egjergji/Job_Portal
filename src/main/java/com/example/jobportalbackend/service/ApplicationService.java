package com.example.jobportalbackend.service;

import com.example.jobportalbackend.exception.ResourceNotFoundException;
import com.example.jobportalbackend.exception.UnauthorizedActionException;
import com.example.jobportalbackend.model.dto.ApplicationDTO;
import com.example.jobportalbackend.model.entity.Application;
import com.example.jobportalbackend.model.entity.Job;
import com.example.jobportalbackend.model.enums.ApplicationStatus;
import com.example.jobportalbackend.repository.ApplicationRepository;
import com.example.jobportalbackend.repository.JobRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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

    // ✅ Get Applications for a Job (Handles Job Not Found)
    public Page<ApplicationDTO> getApplicationsForJob(Long jobId, ApplicationStatus status, int page) {
        Pageable fixedPageable = PageRequest.of(page, 10);
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job with ID " + jobId + " not found"));

        Page<Application> applicationPage = (status != null)
                ? applicationRepository.findByJobAndStatus(job, status, fixedPageable )
                : applicationRepository.findByJob(job, fixedPageable);

        List<ApplicationDTO> dtos = applicationPage.getContent().stream()
                .map(app -> new ApplicationDTO(app.getId(), app.getJob(), app.getJobSeeker(), app.getStatus()))
                .collect(Collectors.toList());

        return new PageImpl<>(dtos, fixedPageable, applicationPage.getTotalElements());
    }

    // ✅ Update Application Status (Handles Unauthorized Access)
    public void updateApplicationStatus(Long applicationId, Long employerId, Long jobId, ApplicationStatus status) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Application with ID " + applicationId + " not found"));

        if (!application.getJob().getEmployer().getId().equals(employerId)) {
            throw new UnauthorizedActionException("Unauthorized: Only the employer who posted this job can update application status.");
        }

        application.setStatus(status);
        applicationRepository.save(application);
    }
}
