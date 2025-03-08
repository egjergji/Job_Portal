package com.example.jobportalbackend.model.dto;

import com.example.jobportalbackend.model.enums.Role;

public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private Role role;
    private String companyName;
    private String companyDescription;
    private String resumeLink;
    private String phoneNumber;

    // ❌ Removed password to prevent security issues
    // ❌ Removed unnecessary constructors

    public UserDTO() {}

    public UserDTO(Long id, String username, Role role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public UserDTO(Long id, String password, String username, Role role,
                   String companyName, String companyDescription,
                   String resumeLink, String phoneNumber) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.companyName = companyName;
        this.companyDescription = companyDescription;
        this.resumeLink = resumeLink;
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
