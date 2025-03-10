package com.example.jobportalbackend.model.entity;

import com.example.jobportalbackend.model.enums.Role;
import jakarta.persistence.*;

@Entity
public class Employer extends User {

    @Column
    private String companyName;

    @Column
    private String companyDescription;

    public Employer() {}

    public Employer(String username, String password, Role role, String companyName) {
        super(username, password, role);
        this.companyName = companyName;
    }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getCompanyDescription() { return companyDescription; }
    public void setCompanyDescription(String companyDescription) { this.companyDescription = companyDescription; }
}
