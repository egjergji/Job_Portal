package com.example.jobportalbackend.model.dto;

import com.example.jobportalbackend.model.entity.Job;
import com.example.jobportalbackend.model.entity.JobSeeker;
import com.example.jobportalbackend.model.enums.ApplicationStatus;

public class ApplicationDTO {
    private Long id;
    private Job job;
    private JobSeeker jobSeeker;
    private ApplicationStatus status;

    public ApplicationDTO() {}

    public ApplicationDTO( Long id, Job job, JobSeeker jobSeeker, ApplicationStatus status) {
        this.id = id;
        this.job = job;
        this.jobSeeker = jobSeeker;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public JobSeeker getJobSeeker() {
        return jobSeeker;
    }

    public void setJobSeeker(JobSeeker jobSeeker) {
        this.jobSeeker = jobSeeker;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }
}
