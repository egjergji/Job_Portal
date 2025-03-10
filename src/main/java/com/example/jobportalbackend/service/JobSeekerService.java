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
import com.example.jobportalbackend.model.entity.ResumeDB;
import com.example.jobportalbackend.model.enums.ApplicationStatus;
import com.example.jobportalbackend.repository.ApplicationRepository;
import com.example.jobportalbackend.repository.JobRepository;
import com.example.jobportalbackend.repository.JobSeekerRepository;
import com.example.jobportalbackend.repository.ResumeDBRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class JobSeekerService {

    private final JobSeekerRepository jobSeekerRepository;
    private final JobRepository jobRepository;
    private final ApplicationRepository applicationRepository;
    private final ResumeDBRepository resumeDBRepository;
    private final JobSeekerMapper jobSeekerMapper;
    private final JobMapper jobMapper;
    private final ApplicationMapper applicationMapper;

    public JobSeekerService(JobSeekerRepository jobSeekerRepository, JobRepository jobRepository,
                            ApplicationRepository applicationRepository, ResumeDBRepository resumeDBRepository, JobSeekerMapper jobSeekerMapper,
                            JobMapper jobMapper, ApplicationMapper applicationMapper) {
        this.jobSeekerRepository = jobSeekerRepository;
        this.jobRepository = jobRepository;
        this.applicationRepository = applicationRepository;
        this.resumeDBRepository = resumeDBRepository;
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

    public void uploadResume(Long jobSeekerId, MultipartFile file) throws IOException {
        JobSeeker jobSeeker = jobSeekerRepository.findById(jobSeekerId)
                .orElseThrow(() -> new ResourceNotFoundException("Job Seeker with ID " + jobSeekerId + " not found"));

        Optional<ResumeDB> existingResume = resumeDBRepository.findByJobSeeker(jobSeeker);
        ResumeDB resume = existingResume.orElse(new ResumeDB());

        resume.setJobSeeker(jobSeeker);
        resume.setName(file.getOriginalFilename());
        resume.setType(file.getContentType());
        resume.setData(file.getBytes());

        resumeDBRepository.save(resume);
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

    public Page<JobDTO> viewAllJobs(String title, String location, Long employerId, Pageable pageable) {
        return jobRepository.findJobsWithFilters(title, location, employerId, pageable)
                .map(jobMapper::toDto);
    }
}
