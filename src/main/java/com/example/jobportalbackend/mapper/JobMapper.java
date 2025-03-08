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
        jobDTO.setEmployerId(job.getEmployer().getId());
        return jobDTO;
    }
}
