package com.example.jobportalbackend.controller;

import com.example.jobportalbackend.model.dto.ApplicationDTO;
import com.example.jobportalbackend.model.dto.JobDTO;
import com.example.jobportalbackend.model.dto.ReviewDTO;
import com.example.jobportalbackend.model.entity.Job;
import com.example.jobportalbackend.model.enums.ApplicationStatus;
import com.example.jobportalbackend.service.ApplicationService;
import com.example.jobportalbackend.service.JobService;
import com.example.jobportalbackend.service.ReviewService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employer")
public class EmployerController {

    private final JobService jobService;
    private final ApplicationService applicationService;
    private final ReviewService reviewService;

    public EmployerController(JobService jobService, ApplicationService applicationService, ReviewService reviewService) {
        this.jobService = jobService;
        this.applicationService = applicationService;
        this.reviewService = reviewService;
    }

    @PostMapping("/{employerId}/jobs")
    @PreAuthorize("hasAuthority('ROLE_EMPLOYER')")
    public JobDTO postJob(@PathVariable Long employerId, @RequestBody Job job) {
        return jobService.createJob(employerId, new JobDTO());
    }

    @GetMapping("/{employerId}/jobs")
    @PreAuthorize("hasAuthority('ROLE_EMPLOYER')")
    public List<JobDTO> getJobsByEmployer(
            @PathVariable Long employerId,
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0") int page) {
        return jobService.getJobsByEmployer(employerId, title, page).getContent();
    }

    @GetMapping("/{employerId}/jobs/{jobId}/applications")
    @PreAuthorize("hasAuthority('ROLE_EMPLOYER')")
    public List<ApplicationDTO> getJobApplications(
            @PathVariable Long employerId, @PathVariable Long jobId,
            @RequestParam(required = false) ApplicationStatus status,
            @RequestParam(defaultValue = "0") int page) {
        return applicationService.getApplicationsForJob(jobId, status, page).getContent();
    }

    @PutMapping("/{employerId}/jobs/{jobId}/applications/{applicationId}/status")
    @PreAuthorize("hasAuthority('ROLE_EMPLOYER')")
    public void updateApplicationStatus(
            @PathVariable Long employerId, @PathVariable Long jobId,
            @PathVariable Long applicationId, @RequestParam ApplicationStatus status) {
        applicationService.updateApplicationStatus(applicationId, employerId, jobId, status);
    }

    @PostMapping("/{employerId}/jobs/{jobId}/reviews")
    @PreAuthorize("hasAuthority('ROLE_EMPLOYER')")
    public ReviewDTO addReview(@PathVariable Long employerId, @PathVariable Long jobId, @RequestBody ReviewDTO reviewDTO) {
        return reviewService.addReview(jobId, employerId, reviewDTO);
    }
}
