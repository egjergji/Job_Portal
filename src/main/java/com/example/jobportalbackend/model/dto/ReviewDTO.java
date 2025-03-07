package com.example.jobportalbackend.model.dto;

import com.example.jobportalbackend.model.entity.Employer;
import com.example.jobportalbackend.model.entity.Job;

public class ReviewDTO {
    private Long id;
    private int rating;
    private String comment;
    private Job job;
    private Employer employer;

    public ReviewDTO() {}

    public ReviewDTO( int rating, String comment, Job job, Employer employer) {
        this.rating = rating;
        this.comment = comment;
        this.job = job;
        this.employer = employer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }
}
