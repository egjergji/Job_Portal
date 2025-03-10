package com.example.jobportalbackend.controller;

import com.example.jobportalbackend.model.dto.ApplicationDTO;
import com.example.jobportalbackend.model.dto.JobDTO;
import com.example.jobportalbackend.security.JwtUtil;
import com.example.jobportalbackend.service.JobSeekerService;
import com.example.jobportalbackend.service.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/jobseeker")
public class JobSeekerController {

    private final JobSeekerService jobSeekerService;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public JobSeekerController(JobSeekerService jobSeekerService, UserService userService, JwtUtil jwtUtil) {
        this.jobSeekerService = jobSeekerService;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    private Long getAuthenticatedJobSeekerId(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String username = jwtUtil.getUsernameFromToken(token);
        return userService.getJobSeekerIdByUsername(username);
    }

    @PostMapping("/apply")
    @PreAuthorize("hasAuthority('ROLE_JOBSEEKER')")
    public ApplicationDTO applyForJob(@RequestParam Long jobId, HttpServletRequest request) {
        Long jobSeekerId = getAuthenticatedJobSeekerId(request);
        return jobSeekerService.applyForJob(jobSeekerId, jobId);
    }

    @PutMapping("/resume")
    @PreAuthorize("hasAuthority('ROLE_JOBSEEKER')")
    public void uploadResume(@RequestParam String resumeLink, HttpServletRequest request) {
        Long jobSeekerId = getAuthenticatedJobSeekerId(request);
        jobSeekerService.uploadResume(jobSeekerId, resumeLink);
    }

    @GetMapping("/applications")
    @PreAuthorize("hasAuthority('ROLE_JOBSEEKER')")
    public List<ApplicationDTO> getApplicationsByJobSeeker(
            @RequestParam(required = false) String jobTitle,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            HttpServletRequest request) {
        Long jobSeekerId = getAuthenticatedJobSeekerId(request);
        return jobSeekerService.getApplicationsByJobSeeker(jobSeekerId, jobTitle, status, page).getContent();
    }

    @GetMapping("/jobs")
    @PreAuthorize("hasAuthority('ROLE_JOBSEEKER')")
    public List<JobDTO> viewAllJobs(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Long employerId,
            @RequestParam(defaultValue = "0") int page) {
        return jobSeekerService.viewAllJobs(title, location, employerId, PageRequest.of(page, 10)).getContent();
    }
}