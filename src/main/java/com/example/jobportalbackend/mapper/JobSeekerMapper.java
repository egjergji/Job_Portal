package com.example.jobportalbackend.mapper;

import com.example.jobportalbackend.model.dto.JobSeekerDTO;
import com.example.jobportalbackend.model.entity.JobSeeker;

public class JobSeekerMapper {

    public static JobSeekerDTO toDTO(JobSeeker jobSeeker) {
        JobSeekerDTO jobSeekerDTO = new JobSeekerDTO();
        jobSeekerDTO.setId(jobSeeker.getId());
        jobSeekerDTO.setUsername(jobSeeker.getUsername());
        jobSeekerDTO.setResumeLink(jobSeeker.getResumeLink());
        jobSeekerDTO.setPhoneNumber(jobSeeker.getPhoneNumber());
        return jobSeekerDTO;
    }
    public static JobSeeker toEntity(JobSeekerDTO jobSeekerDTO) {
        JobSeeker jobSeeker = new JobSeeker();
        jobSeeker.setUsername(jobSeekerDTO.getUsername());// Password should be set separately
        jobSeeker.setResumeLink(jobSeekerDTO.getResumeLink());
        jobSeeker.setPhoneNumber(jobSeekerDTO.getPhoneNumber());
        return jobSeeker;
    }
}
