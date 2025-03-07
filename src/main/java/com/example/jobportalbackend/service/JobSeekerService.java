package com.example.jobportalbackend.service;

import com.example.jobportalbackend.mapper.JobSeekerMapper;
import com.example.jobportalbackend.model.dto.JobSeekerDTO;
import com.example.jobportalbackend.model.entity.JobSeeker;
import com.example.jobportalbackend.repository.JobSeekerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobSeekerService {

    private final JobSeekerRepository jobSeekerRepository;

    public JobSeekerService(JobSeekerRepository jobSeekerRepository) {
        this.jobSeekerRepository = jobSeekerRepository;
    }

    public List<JobSeekerDTO> getAllJobSeekers() {
        return jobSeekerRepository.findAll()
                .stream()
                .map(JobSeekerMapper::toDTO)
                .collect(Collectors.toList());
    }

    public JobSeekerDTO getJobSeekerById(Long id) {
        return jobSeekerRepository.findById(id)
                .map(JobSeekerMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Job Seeker not found"));
    }

    public JobSeekerDTO createJobSeeker(JobSeekerDTO jobSeekerDTO) {
        JobSeeker jobSeeker = JobSeekerMapper.toEntity(jobSeekerDTO);
        jobSeeker.setPassword("DEFAULT_PASSWORD");  // Set password separately
        JobSeeker savedJobSeeker = jobSeekerRepository.save(jobSeeker);
        return JobSeekerMapper.toDTO(savedJobSeeker);
    }

    public void deleteJobSeeker(Long id) {
        jobSeekerRepository.deleteById(id);
    }
}
