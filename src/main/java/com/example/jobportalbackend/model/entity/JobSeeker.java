package com.example.jobportalbackend.model.entity;

import com.example.jobportalbackend.model.enums.Role;
import jakarta.persistence.*;

@Entity
public class JobSeeker extends User {

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    public JobSeeker() {}

    public JobSeeker(String username, String password, Role role, String email, String phoneNumber) {
        super(username, password, role);
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
}
