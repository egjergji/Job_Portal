package com.example.jobportalbackend.model.dto;

import com.example.jobportalbackend.model.enums.Role;

public class UserDTO {
    private String username;
    private Role role;
    private String companyName;
    private String companyDescription;
    private String phoneNumber;


    public UserDTO() {}

    public UserDTO(String username, Role role) {
        this.username = username;
        this.role = role;
    }

    public UserDTO(String username, Role role,
                   String companyName, String companyDescription,
                   String phoneNumber) {
        this.username = username;
        this.role = role;
        this.companyName = companyName;
        this.companyDescription = companyDescription;
        this.phoneNumber = phoneNumber;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
