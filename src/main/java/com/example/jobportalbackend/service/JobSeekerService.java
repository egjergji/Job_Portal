package com.example.jobportalbackend.service;

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

    // ✅ Apply for a Job
    public ApplicationDTO applyForJob(Long jobSeekerId, Long jobId) {
        JobSeeker jobSeeker = jobSeekerRepository.findById(jobSeekerId)
                .orElseThrow(() -> new RuntimeException("Job Seeker not found"));

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        Application application = new Application();
        application.setJobSeeker(jobSeeker);
        application.setJob(job);
        application.setStatus(ApplicationStatus.PENDING);

        Application savedApplication = applicationRepository.save(application);

        return new ApplicationDTO(savedApplication.getId(), savedApplication.getJob(), savedApplication.getJobSeeker(), savedApplication.getStatus());
    }

    // ✅ Upload Resume
    public void uploadResume(Long jobSeekerId, String resumeLink) {
        JobSeeker jobSeeker = jobSeekerRepository.findById(jobSeekerId)
                .orElseThrow(() -> new RuntimeException("Job Seeker not found"));

        jobSeeker.setResumeLink(resumeLink);
        jobSeekerRepository.save(jobSeeker);
    }


    public Page<ApplicationDTO> getApplicationsByJobSeeker(Long jobSeekerId, String jobTitle, String status, Pageable pageable) {
        return applicationRepository.findApplicationsByJobSeeker(jobSeekerId, jobTitle, status, pageable)
                .map(app -> new ApplicationDTO(app.getId(), app.getJob(), app.getJobSeeker(), app.getStatus()));
    }

    public Page<JobDTO> viewAllJobs(String title, Long employerId, Pageable pageable) {
        return jobRepository.findJobsWithFilters(title, employerId, pageable)
                .map(job -> new JobDTO(job.getId(), job.getTitle(), job.getDescription(), job.getEmployer().getId()));
    }
}
