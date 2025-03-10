package com.example.jobportalbackend.service;

import com.example.jobportalbackend.exception.ResourceNotFoundException;
import com.example.jobportalbackend.exception.UnauthorizedActionException;
import com.example.jobportalbackend.mapper.ApplicationMapper;
import com.example.jobportalbackend.model.dto.ApplicationDTO;
import com.example.jobportalbackend.model.entity.Application;
import com.example.jobportalbackend.model.entity.Job;
import com.example.jobportalbackend.model.enums.ApplicationStatus;
import com.example.jobportalbackend.repository.ApplicationRepository;
import com.example.jobportalbackend.repository.JobRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final ApplicationMapper applicationMapper;

    public ApplicationService(ApplicationRepository applicationRepository,
                              JobRepository jobRepository,
                              ApplicationMapper applicationMapper) {
        this.applicationRepository = applicationRepository;
        this.jobRepository = jobRepository;
        this.applicationMapper = applicationMapper;
    }


    public Page<ApplicationDTO> getApplicationsForJob(Long employerId, Long jobId, ApplicationStatus status, int page) {
        Pageable fixedPageable = PageRequest.of(page, 10);

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job with ID " + jobId + " not found"));

        if (!job.getEmployer().getId().equals(employerId)) {
            throw new UnauthorizedActionException("Unauthorized: You can only view applications for jobs you posted.");
        }

        Page<Application> applicationPage = (status != null)
                ? applicationRepository.findByJobAndStatus(job, status, fixedPageable)
                : applicationRepository.findByJob(job, fixedPageable);

        return applicationPage.map(applicationMapper::toDto);
    }

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
