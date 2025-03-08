package com.example.jobportalbackend.model.entity;

import com.example.jobportalbackend.model.enums.Role;
import jakarta.persistence.*;

@Entity
public class Employer extends User {

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String companyDescription;

    public Employer() {}

    public Employer(String username, String password, Role role, String companyName, String companyDescription) {
        super(username, password, role);
        this.companyName = companyName;
        this.companyDescription = companyDescription;
    }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getCompanyDescription() { return companyDescription; }
    public void setCompanyDescription(String companyDescription) { this.companyDescription = companyDescription; }
}
