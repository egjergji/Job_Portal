package com.example.jobportalbackend.model.entity;

import com.example.jobportalbackend.model.enums.Role;
import jakarta.persistence.*;

@Entity
public class JobSeeker extends User {

    @Column
    private String phoneNumber;

    public JobSeeker() {}

    public JobSeeker(String username, String password, Role role,  String phoneNumber) {
        super(username, password, role);
        this.phoneNumber = phoneNumber;
    }


    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
}
