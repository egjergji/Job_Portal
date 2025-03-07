package com.example.jobportalbackend.controller;

import com.example.jobportalbackend.model.dto.ApplicationDTO;
import com.example.jobportalbackend.model.enums.ApplicationStatus;
import com.example.jobportalbackend.service.ApplicationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    public ApplicationDTO applyForJob(@RequestParam Long jobSeekerId, @RequestParam Long jobId) {
        return applicationService.applyForJob(jobSeekerId, jobId);
    }

    @GetMapping("/seeker/{jobSeekerId}")
    public List<ApplicationDTO> getApplicationsByJobSeeker(@PathVariable Long jobSeekerId) {
        return applicationService.getApplicationsByJobSeeker(jobSeekerId);
    }

    @GetMapping("/job/{jobId}")
    public List<ApplicationDTO> getApplicationsForJob(@PathVariable Long jobId) {
        return applicationService.getApplicationsForJob(jobId);
    }

    @PutMapping("/{applicationId}/status")
    public void updateApplicationStatus(@PathVariable Long applicationId, @RequestParam ApplicationStatus status) {
        applicationService.updateApplicationStatus(applicationId, status);
    }
}
