package com.example.jobportalbackend.model.dto;

public class RegisterRequest {

    private UserDTO userDetails;
    private String password;

    public UserDTO getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDTO userDetails) {
        this.userDetails = userDetails;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
