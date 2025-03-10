package com.example.jobportalbackend.mapper;

import com.example.jobportalbackend.model.dto.EmployerDTO;
import com.example.jobportalbackend.model.entity.Employer;
import org.springframework.stereotype.Component;

@Component
public class EmployerMapper extends AbstractMapper<Employer, EmployerDTO> {

    @Override
    public EmployerDTO toDto(Employer employer) {
        if (employer == null) {
            return null;
        }
        EmployerDTO employerDTO = new EmployerDTO();
        employerDTO.setUsername(employer.getUsername());
        employerDTO.setCompanyName(employer.getCompanyName());
        employerDTO.setCompanyDescription(employer.getCompanyDescription());
        return employerDTO;
    }

    @Override
    public Employer toEntity(EmployerDTO employerDTO) {
        if (employerDTO == null) {
            return null;
        }
        Employer employer = new Employer();
        employer.setUsername(employerDTO.getUsername());
        employer.setCompanyName(employerDTO.getCompanyName());
        employer.setCompanyDescription(employerDTO.getCompanyDescription());
        return employer;
    }
}
