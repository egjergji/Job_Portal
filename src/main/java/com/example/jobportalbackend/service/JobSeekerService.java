package com.example.jobportalbackend.service;

import com.example.jobportalbackend.exception.ResourceNotFoundException;
import com.example.jobportalbackend.exception.UnauthorizedActionException;
import com.example.jobportalbackend.mapper.JobSeekerMapper;
import com.example.jobportalbackend.mapper.JobMapper;
import com.example.jobportalbackend.mapper.ApplicationMapper;
import com.example.jobportalbackend.model.dto.ApplicationDTO;
import com.example.jobportalbackend.model.dto.JobDTO;
import com.example.jobportalbackend.model.dto.JobSeekerDTO;
import com.example.jobportalbackend.model.entity.Application;
import com.example.jobportalbackend.model.entity.Job;
import com.example.jobportalbackend.model.entity.JobSeeker;
import com.example.jobportalbackend.model.enums.ApplicationStatus;
import com.example.jobportalbackend.repository.ApplicationRepository;
import com.example.jobportalbackend.repository.JobRepository;
import com.example.jobportalbackend.repository.JobSeekerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class JobSeekerService {

    private final JobSeekerRepository jobSeekerRepository;
    private final JobRepository jobRepository;
    private final ApplicationRepository applicationRepository;
    private final JobSeekerMapper jobSeekerMapper;
    private final JobMapper jobMapper;
    private final ApplicationMapper applicationMapper;

    public JobSeekerService(JobSeekerRepository jobSeekerRepository, JobRepository jobRepository,
                            ApplicationRepository applicationRepository, JobSeekerMapper jobSeekerMapper,
                            JobMapper jobMapper, ApplicationMapper applicationMapper) {
        this.jobSeekerRepository = jobSeekerRepository;
        this.jobRepository = jobRepository;
        this.applicationRepository = applicationRepository;
        this.jobSeekerMapper = jobSeekerMapper;
        this.jobMapper = jobMapper;
        this.applicationMapper = applicationMapper;
    }

    public ApplicationDTO applyForJob(Long jobSeekerId, Long jobId) {
        JobSeeker jobSeeker = jobSeekerRepository.findById(jobSeekerId)
                .orElseThrow(() -> new ResourceNotFoundException("Job Seeker with ID " + jobSeekerId + " not found"));

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job with ID " + jobId + " not found"));

        if (applicationRepository.existsByJobSeekerAndJob(jobSeeker, job)) {
            throw new IllegalStateException("You have already applied for this job.");
        }

        Application application = new Application();
        application.setJobSeeker(jobSeeker);
        application.setJob(job);
        application.setStatus(ApplicationStatus.PENDING);

        Application savedApplication = applicationRepository.save(application);
        return applicationMapper.toDto(savedApplication);
    }

    public void uploadResume(Long jobSeekerId, String resumeLink) {
        JobSeeker jobSeeker = jobSeekerRepository.findById(jobSeekerId)
                .orElseThrow(() -> new ResourceNotFoundException("Job Seeker with ID " + jobSeekerId + " not found"));

        jobSeeker.setResumeLink(resumeLink);
        jobSeekerRepository.save(jobSeeker);
    }

    public Page<ApplicationDTO> getApplicationsByJobSeeker(Long jobSeekerId, String jobTitle, String status, int page) {
        Pageable fixedPageable = PageRequest.of(page, 10);
        JobSeeker jobSeeker = jobSeekerRepository.findById(jobSeekerId)
                .orElseThrow(() -> new ResourceNotFoundException("Job Seeker with ID " + jobSeekerId + " not found"));

        return applicationRepository.findApplicationsByJobSeeker(jobSeekerId, jobTitle, status, fixedPageable)
                .map(applicationMapper::toDto);
    }

    public JobSeekerDTO getJobSeekerById(Long jobSeekerId, Long requestingUserId) {
        if (!jobSeekerId.equals(requestingUserId)) {
            throw new UnauthorizedActionException("You can only view your own profile.");
        }
        JobSeeker jobSeeker = jobSeekerRepository.findById(jobSeekerId)
                .orElseThrow(() -> new ResourceNotFoundException("Job Seeker with ID " + jobSeekerId + " not found"));

        return jobSeekerMapper.toDto(jobSeeker);
    }

    public Page<JobDTO> viewAllJobs(String title, Long employerId, Pageable pageable) {
        return jobRepository.findJobsWithFilters(title, employerId, pageable)
                .map(jobMapper::toDto);
    }
}
