package com.example.jobportalbackend.model.dto;

import com.example.jobportalbackend.model.enums.Role;

public class EmployerDTO extends UserDTO {
    private String companyName;
    private String companyDescription;

    public EmployerDTO() {}

    public EmployerDTO(Long id, String username,Role role, String companyName, String companyDescription) {
        super(id, username, role);
        this.companyName = companyName;
        this.companyDescription = companyDescription;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }
}
