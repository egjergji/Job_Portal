package com.example.jobportalbackend.model.dto;

import com.example.jobportalbackend.model.entity.Employer;

public class JobDTO {
    private Long id;
    private String title;
    private String description;
    private Employer employer;

    public JobDTO() {}

    public JobDTO(String title, String description, Employer employer) {
        this.title = title;
        this.description = description;
        this.employer = employer;
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

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }
}
