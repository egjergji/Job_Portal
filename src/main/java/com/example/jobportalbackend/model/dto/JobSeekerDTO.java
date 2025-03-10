package com.example.jobportalbackend.model.dto;

import com.example.jobportalbackend.model.enums.Role;

public class JobSeekerDTO extends UserDTO {
    private String email;
    private String phoneNumber;

    public JobSeekerDTO() {}

    public JobSeekerDTO(Long id, String username, String phoneNumber) {
        super(username, Role.JOBSEEKER);
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
