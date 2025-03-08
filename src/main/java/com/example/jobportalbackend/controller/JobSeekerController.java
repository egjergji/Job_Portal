package com.example.jobportalbackend.controller;

import com.example.jobportalbackend.model.dto.ApplicationDTO;
import com.example.jobportalbackend.model.dto.JobDTO;
import com.example.jobportalbackend.service.JobSeekerService;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobseeker")
public class JobSeekerController {

    private final JobSeekerService jobSeekerService;

    public JobSeekerController(JobSeekerService jobSeekerService) {
        this.jobSeekerService = jobSeekerService;
    }

    // ✅ Apply for a job
    @PostMapping("/{jobSeekerId}/apply")
    @PreAuthorize("hasAuthority('ROLE_JOBSEEKER')")
    public ApplicationDTO applyForJob(
            @PathVariable Long jobSeekerId,
            @RequestParam Long jobId) {
        return jobSeekerService.applyForJob(jobSeekerId, jobId);
    }

    // ✅ Upload Resume
    @PutMapping("/{jobSeekerId}/resume")
    @PreAuthorize("hasAuthority('ROLE_JOBSEEKER')")
    public void uploadResume(@PathVariable Long jobSeekerId, @RequestParam String resumeLink) {
        jobSeekerService.uploadResume(jobSeekerId, resumeLink);
    }

    // ✅ Get all applications (Paginated & Filtered)
    @GetMapping("/{jobSeekerId}/applications")
    @PreAuthorize("hasAuthority('ROLE_JOBSEEKER')")
    public List<ApplicationDTO> getApplicationsByJobSeeker(
            @PathVariable Long jobSeekerId,
            @RequestParam(required = false) String jobTitle,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page) {
        return jobSeekerService.getApplicationsByJobSeeker(jobSeekerId, jobTitle, status, PageRequest.of(page, 10)).getContent();
    }

    // ✅ View all jobs (Paginated & Filtered, Excluding Location)
    @GetMapping("/jobs")
    public List<JobDTO> viewAllJobs(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Long employerId,
            @RequestParam(defaultValue = "0") int page) {
        return jobSeekerService.viewAllJobs(title, employerId, PageRequest.of(page, 10)).getContent();
    }
}
