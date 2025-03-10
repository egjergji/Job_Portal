package com.example.jobportalbackend.model.dto;

import com.example.jobportalbackend.model.enums.Role;

public class JobSeekerDTO extends UserDTO {
    private String email;
    private String phoneNumber;

    public JobSeekerDTO() {}

    public JobSeekerDTO(Long id, String username, String email, String phoneNumber) {
        super(username, Role.JOBSEEKER);
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
