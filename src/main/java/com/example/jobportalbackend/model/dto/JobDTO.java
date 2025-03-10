package com.example.jobportalbackend.model.dto;

public class JobDTO {
    private Long id;
    private String title;
    private String location;
    private String description;
    private Long employerId;

    public JobDTO() {}


    public JobDTO(Long id, String title, String location, String description, Long employerId) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.description = description;
        this.employerId = employerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getEmployerId() {
        return employerId;
    }

    public void setEmployerId(Long employerId) {
        this.employerId = employerId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
