package com.example.jobportalbackend.mapper;

import com.example.jobportalbackend.model.dto.JobSeekerDTO;
import com.example.jobportalbackend.model.entity.JobSeeker;
import org.springframework.stereotype.Component;

@Component
public class JobSeekerMapper extends AbstractMapper<JobSeeker, JobSeekerDTO> {

    @Override
    public JobSeekerDTO toDto(JobSeeker jobSeeker) {
        if (jobSeeker == null) {
            return null;
        }
        JobSeekerDTO jobSeekerDTO = new JobSeekerDTO();
        jobSeekerDTO.setUsername(jobSeeker.getUsername());
        jobSeekerDTO.setEmail(jobSeeker.getEmail());
        jobSeekerDTO.setPhoneNumber(jobSeeker.getPhoneNumber());
        return jobSeekerDTO;
    }

    @Override
    public JobSeeker toEntity(JobSeekerDTO jobSeekerDTO) {
        if (jobSeekerDTO == null) {
            return null;
        }
        JobSeeker jobSeeker = new JobSeeker();
        jobSeeker.setUsername(jobSeekerDTO.getUsername());
        jobSeeker.setEmail(jobSeekerDTO.getEmail());
        jobSeeker.setPhoneNumber(jobSeekerDTO.getPhoneNumber());
        return jobSeeker;
    }
}
