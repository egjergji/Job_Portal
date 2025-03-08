package com.example.jobportalbackend.controller;

import com.example.jobportalbackend.model.entity.Job;
import com.example.jobportalbackend.service.JobService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping("/{employerId}")
    public Job createJob(@PathVariable Long employerId, @RequestBody Job job) {
        return jobService.createJob(employerId, job);
    }

}
