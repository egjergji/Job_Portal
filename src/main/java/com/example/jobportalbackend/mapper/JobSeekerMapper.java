package com.example.jobportalbackend.mapper;

import com.example.jobportalbackend.model.dto.JobSeekerDTO;
import com.example.jobportalbackend.model.entity.JobSeeker;
import org.springframework.stereotype.Component;

@Component
public class JobSeekerMapper extends AbstractMapper<JobSeeker, JobSeekerDTO> {
    @Override
    public JobSeekerDTO toDto(JobSeeker jobSeeker) {
        JobSeekerDTO jobSeekerDTO = new JobSeekerDTO();
        jobSeekerDTO.setId(jobSeeker.getId());
        jobSeekerDTO.setUsername(jobSeeker.getUsername());
        jobSeekerDTO.setResumeLink(jobSeeker.getResumeLink());
        jobSeekerDTO.setPhoneNumber(jobSeeker.getPhoneNumber());
        return jobSeekerDTO;
    }

    @Override
    public JobSeeker toEntity(JobSeekerDTO jobSeekerDTO) {
        JobSeeker jobSeeker = new JobSeeker();
        jobSeeker.setUsername(jobSeekerDTO.getUsername());
        jobSeeker.setResumeLink(jobSeekerDTO.getResumeLink());
        jobSeeker.setPhoneNumber(jobSeekerDTO.getPhoneNumber());
        return jobSeeker;
    }
}
