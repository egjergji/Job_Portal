package com.example.jobportalbackend.service;

import com.example.jobportalbackend.exception.ResourceNotFoundException;
import com.example.jobportalbackend.mapper.JobMapper;
import com.example.jobportalbackend.model.dto.JobDTO;
import com.example.jobportalbackend.model.entity.Job;
import com.example.jobportalbackend.model.entity.Employer;
import com.example.jobportalbackend.repository.JobRepository;
import com.example.jobportalbackend.repository.EmployerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private final EmployerRepository employerRepository;
    private final JobMapper jobMapper;

    public JobService(JobRepository jobRepository, EmployerRepository employerRepository, JobMapper jobMapper) {
        this.jobRepository = jobRepository;
        this.employerRepository = employerRepository;
        this.jobMapper = jobMapper;
    }


    public JobDTO createJob(Long employerId, JobDTO jobDTO) {
        Employer employer = employerRepository.findById(employerId)
                .orElseThrow(() -> new ResourceNotFoundException("Employer with ID " + employerId + " not found"));

        Job job = jobMapper.toEntity(jobDTO);
        job.setEmployer(employer);

        Job savedJob = jobRepository.save(job);
        return jobMapper.toDto(savedJob);
    }


    public Page<JobDTO> getJobsByEmployer(Long employerId, String title, String location, int page) {
        Employer employer = employerRepository.findById(employerId)
                .orElseThrow(() -> new ResourceNotFoundException("Employer with ID " + employerId + " not found"));

        Pageable fixedPageable = PageRequest.of(page, 10);

        Page<Job> jobs = ((title != null && !title.isEmpty()) || (location != null && !location.isEmpty()))
                ? jobRepository.findByEmployerAndTitleContainingIgnoreCaseOrLocationContainingIgnoreCase(employer, title, location, fixedPageable)
                : jobRepository.findByEmployer(employer, fixedPageable);

        return jobs.map(jobMapper::toDto);
    }
}
