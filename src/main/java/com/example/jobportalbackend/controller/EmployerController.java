package com.example.jobportalbackend.controller;

import com.example.jobportalbackend.model.dto.ApplicationDTO;
import com.example.jobportalbackend.model.dto.JobDTO;
import com.example.jobportalbackend.model.dto.ReviewDTO;
import com.example.jobportalbackend.model.enums.ApplicationStatus;
import com.example.jobportalbackend.security.JwtUtil;
import com.example.jobportalbackend.service.ApplicationService;
import com.example.jobportalbackend.service.JobService;
import com.example.jobportalbackend.service.ReviewService;
import com.example.jobportalbackend.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/employer")
public class EmployerController {

    private final JobService jobService;
    private final ApplicationService applicationService;
    private final ReviewService reviewService;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public EmployerController(JobService jobService, ApplicationService applicationService, ReviewService reviewService, UserService userService, JwtUtil jwtUtil) {
        this.jobService = jobService;
        this.applicationService = applicationService;
        this.reviewService = reviewService;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    private Long getAuthenticatedEmployerId(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String username = jwtUtil.getUsernameFromToken(token);
        return userService.getEmployerIdByUsername(username);
    }

    @PostMapping("/jobs")
    @PreAuthorize("hasAuthority('ROLE_EMPLOYER')")
    public JobDTO postJob(@RequestBody JobDTO jobDTO, HttpServletRequest request) {
        Long employerId = getAuthenticatedEmployerId(request);
        return jobService.createJob(employerId, jobDTO);
    }

    @GetMapping("/jobs")
    @PreAuthorize("hasAuthority('ROLE_EMPLOYER')")
    public List<JobDTO> getJobsByEmployer(
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0") int page,
            HttpServletRequest request) {
        Long employerId = getAuthenticatedEmployerId(request);
        return jobService.getJobsByEmployer(employerId, title, page).getContent();
    }

    @GetMapping("/jobs/{jobId}/applications")
    @PreAuthorize("hasAuthority('ROLE_EMPLOYER')")
    public List<ApplicationDTO> getApplicationsForJob(
            @PathVariable Long jobId,
            @RequestParam(required = false) ApplicationStatus status,
            @RequestParam(defaultValue = "0") int page,
            HttpServletRequest request) {
        Long employerId = getAuthenticatedEmployerId(request);
        return applicationService.getApplicationsForJob(employerId, jobId, status, page).getContent();
    }

    @PutMapping("/jobs/{jobId}/applications/{applicationId}/status")
    @PreAuthorize("hasAuthority('ROLE_EMPLOYER')")
    public void updateApplicationStatus(
            @PathVariable Long jobId,
            @PathVariable Long applicationId,
            @RequestParam ApplicationStatus status,
            HttpServletRequest request) {
        Long employerId = getAuthenticatedEmployerId(request);
        applicationService.updateApplicationStatus(applicationId, employerId, jobId, status);
    }

    @PostMapping("/jobs/{jobId}/reviews")
    @PreAuthorize("hasAuthority('ROLE_EMPLOYER')")
    public ReviewDTO addReview(@PathVariable Long jobId, @RequestBody ReviewDTO reviewDTO, HttpServletRequest request) {
        Long employerId = getAuthenticatedEmployerId(request);
        return reviewService.addReview(jobId, employerId, reviewDTO);
    }
}