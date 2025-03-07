package com.example.jobportalbackend.controller;

import com.example.jobportalbackend.model.dto.JobSeekerDTO;
import com.example.jobportalbackend.service.JobSeekerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobseekers")
public class JobSeekerController {

    private final JobSeekerService jobSeekerService;

    public JobSeekerController(JobSeekerService jobSeekerService) {
        this.jobSeekerService = jobSeekerService;
    }

    @GetMapping
    public List<JobSeekerDTO> getAllJobSeekers() {
        return jobSeekerService.getAllJobSeekers();
    }

    @GetMapping("/{id}")
    public JobSeekerDTO getJobSeekerById(@PathVariable Long id) {
        return jobSeekerService.getJobSeekerById(id);
    }

    @PostMapping
    public JobSeekerDTO createJobSeeker(@RequestBody JobSeekerDTO jobSeekerDTO) {
        return jobSeekerService.createJobSeeker(jobSeekerDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteJobSeeker(@PathVariable Long id) {
        jobSeekerService.deleteJobSeeker(id);
    }
}
