package com.example.jobportalbackend.service;

import com.example.jobportalbackend.exception.ResourceNotFoundException;
import com.example.jobportalbackend.exception.DuplicateResourceException;
import com.example.jobportalbackend.mapper.EmployerMapper;
import com.example.jobportalbackend.model.dto.EmployerDTO;
import com.example.jobportalbackend.model.entity.Employer;
import com.example.jobportalbackend.repository.EmployerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployerService {

    private final EmployerRepository employerRepository;
    private final EmployerMapper employerMapper;

    public EmployerService(EmployerRepository employerRepository, EmployerMapper employerMapper) {
        this.employerRepository = employerRepository;
        this.employerMapper = employerMapper;
    }


    public EmployerDTO getEmployerById(Long id) {
        Employer employer = employerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employer with ID " + id + " not found"));

        return employerMapper.toDto(employer);
    }


    public EmployerDTO createEmployer(EmployerDTO employerDTO) {
        if (employerRepository.findByUsername(employerDTO.getUsername()).isPresent()) {
            throw new DuplicateResourceException("Employer with username '" + employerDTO.getUsername() + "' already exists");
        }

        Employer employer = employerMapper.toEntity(employerDTO);
        Employer savedEmployer = employerRepository.save(employer);
        return employerMapper.toDto(savedEmployer);
    }


    public void deleteEmployer(Long id) {
        if (!employerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employer with ID " + id + " not found");
        }
        employerRepository.deleteById(id);
    }
}
