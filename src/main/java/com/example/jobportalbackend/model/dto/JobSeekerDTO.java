package com.example.jobportalbackend.model.dto;

import com.example.jobportalbackend.model.enums.Role;

public class JobSeekerDTO extends UserDTO {
    private String resumeLink;
    private String phoneNumber;

    public JobSeekerDTO() {}

    public JobSeekerDTO(Long id, String username, String resumeLink, String phoneNumber) {
        super(id, username, Role.JOBSEEKER);
        this.resumeLink = resumeLink;
        this.phoneNumber = phoneNumber;
    }

    public String getResumeLink() {
        return resumeLink;
    }

    public void setResumeLink(String resumeLink) {
        this.resumeLink = resumeLink;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
