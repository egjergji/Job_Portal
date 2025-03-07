package com.example.jobportalbackend.service;

import com.example.jobportalbackend.mapper.EmployerMapper;
import com.example.jobportalbackend.model.dto.EmployerDTO;
import com.example.jobportalbackend.model.entity.Employer;
import com.example.jobportalbackend.repository.EmployerRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployerService {

    private final EmployerRepository employerRepository;

    public EmployerService(EmployerRepository employerRepository) {
        this.employerRepository = employerRepository;
    }

    public EmployerDTO getEmployerById(Long id) {
        Employer employer = employerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employer not found"));
        return EmployerMapper.toDTO(employer);
    }
}
