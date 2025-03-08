package com.example.jobportalbackend.model.entity;

import com.example.jobportalbackend.model.enums.Role;
import jakarta.persistence.*;

@Entity
public class JobSeeker extends User {

    @Column(nullable = false)
    private String resumeLink;

    @Column(nullable = false)
    private String phoneNumber;

    public JobSeeker() {}

    public JobSeeker(String username, String password, Role role, String resumeLink, String phoneNumber) {
        super(username, password, role);
        this.resumeLink = resumeLink;
        this.phoneNumber = phoneNumber;
    }

    public String getResumeLink() { return resumeLink; }
    public void setResumeLink(String resumeLink) { this.resumeLink = resumeLink; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
}
