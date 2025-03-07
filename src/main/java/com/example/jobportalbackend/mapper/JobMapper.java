package com.example.jobportalbackend.mapper;

import com.example.jobportalbackend.model.dto.JobDTO;
import com.example.jobportalbackend.model.entity.Job;

public class JobMapper extends AbstractMapper<Job, JobDTO> {

    @Override
    public Job toEntity(JobDTO jobDTO) {
        Job job = new Job();
        job.setId(jobDTO.getId());
        job.setTitle(jobDTO.getTitle());
        job.setDescription(jobDTO.getDescription());
        job.setEmployer(jobDTO.getEmployer());
        return job;
    }

    @Override
    public JobDTO toDto(Job job) {
        if (job == null) {
            return null;
        }
        JobDTO jobDTO = new JobDTO();
        jobDTO.setId(job.getId());
        jobDTO.setTitle(job.getTitle());
        jobDTO.setDescription(job.getDescription());
        jobDTO.setEmployer(job.getEmployer());
        return jobDTO;
    }
}
