package com.example.jobportalbackend.service;

import com.example.jobportalbackend.exception.ResourceNotFoundException;
import com.example.jobportalbackend.model.dto.ApplicationDTO;
import com.example.jobportalbackend.model.dto.JobDTO;
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

    public JobSeekerService(JobSeekerRepository jobSeekerRepository, JobRepository jobRepository, ApplicationRepository applicationRepository) {
        this.jobSeekerRepository = jobSeekerRepository;
        this.jobRepository = jobRepository;
        this.applicationRepository = applicationRepository;
    }

    // ✅ Apply for a Job (Handles Job & Job Seeker Not Found)
    public ApplicationDTO applyForJob(Long jobSeekerId, Long jobId) {
        JobSeeker jobSeeker = jobSeekerRepository.findById(jobSeekerId)
                .orElseThrow(() -> new ResourceNotFoundException("Job Seeker with ID " + jobSeekerId + " not found"));

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job with ID " + jobId + " not found"));

        Application application = new Application();
        application.setJobSeeker(jobSeeker);
        application.setJob(job);
        application.setStatus(ApplicationStatus.PENDING);

        Application savedApplication = applicationRepository.save(application);

        return new ApplicationDTO(savedApplication.getId(), savedApplication.getJob(), savedApplication.getJobSeeker(), savedApplication.getStatus());
    }

    // ✅ Upload Resume (Handles Job Seeker Not Found)
    public void uploadResume(Long jobSeekerId, String resumeLink) {
        JobSeeker jobSeeker = jobSeekerRepository.findById(jobSeekerId)
                .orElseThrow(() -> new ResourceNotFoundException("Job Seeker with ID " + jobSeekerId + " not found"));

        jobSeeker.setResumeLink(resumeLink);
        jobSeekerRepository.save(jobSeeker);
    }

    // ✅ Get Job Applications for a Job Seeker (Handles Job Seeker Not Found)
    public Page<ApplicationDTO> getApplicationsByJobSeeker(Long jobSeekerId, String jobTitle, String status, int page) {
        Pageable fixedPageable = PageRequest.of(page, 10);
        if (!jobSeekerRepository.existsById(jobSeekerId)) {
            throw new ResourceNotFoundException("Job Seeker with ID " + jobSeekerId + " not found");
        }

        return applicationRepository.findApplicationsByJobSeeker(jobSeekerId, jobTitle, status, fixedPageable)
                .map(app -> new ApplicationDTO(app.getId(), app.getJob(), app.getJobSeeker(), app.getStatus()));
    }


    public Page<JobDTO> viewAllJobs(String title, Long employerId, Pageable pageable) {
        return jobRepository.findJobsWithFilters(title, employerId, pageable)
                .map(job -> new JobDTO(job.getId(), job.getTitle(), job.getDescription(), job.getEmployer().getId()));
    }
}
