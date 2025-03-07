package com.example.jobportalbackend.mapper;

import com.example.jobportalbackend.model.dto.EmployerDTO;
import com.example.jobportalbackend.model.entity.Employer;

public class EmployerMapper {
    public static EmployerDTO toDTO(Employer employer) {
        EmployerDTO employerDTO = new EmployerDTO();
        employerDTO.setId(employer.getId());
        employerDTO.setUsername(employer.getUsername());
        employerDTO.setCompanyName(employer.getCompanyName());
        employerDTO.setCompanyDescription(employer.getCompanyDescription());
        return employerDTO;
    }

    public static Employer toEntity(EmployerDTO employerDTO) {
        Employer employer = new Employer();
        employer.setUsername(employerDTO.getUsername());
        employer.setCompanyName(employerDTO.getCompanyName());
        employer.setCompanyDescription(employerDTO.getCompanyDescription());
        return employer;
    }
}
