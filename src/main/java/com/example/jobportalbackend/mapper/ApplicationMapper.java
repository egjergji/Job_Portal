package com.example.jobportalbackend.mapper;

import com.example.jobportalbackend.model.dto.ApplicationDTO;
import com.example.jobportalbackend.model.entity.Application;

public class ApplicationMapper extends AbstractMapper<Application, ApplicationDTO> {

    @Override
    public Application toEntity(ApplicationDTO applicationDTO) {
        Application application = new Application();
        application.setId(applicationDTO.getId());
        application.setJob(applicationDTO.getJob());
        application.setJobSeeker(applicationDTO.getJobSeeker());
        application.setStatus(applicationDTO.getStatus());
        return application;
    }

    @Override
    public ApplicationDTO toDto(Application application) {
        if (application == null) {
            return null;
        }
        ApplicationDTO applicationDTO = new ApplicationDTO();
        applicationDTO.setId(application.getId());
        applicationDTO.setJob(application.getJob());
        applicationDTO.setJobSeeker(application.getJobSeeker());
        applicationDTO.setStatus(application.getStatus());
        return applicationDTO;
    }
}
