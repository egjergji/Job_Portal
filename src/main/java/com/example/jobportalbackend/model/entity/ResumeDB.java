package com.example.jobportalbackend.model.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "files")
public class ResumeDB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;

    @Lob
    private byte[] data;

    @OneToOne
    @JoinColumn(name = "job_seeker_id" , nullable = false)
    private JobSeeker jobSeeker;

    public ResumeDB() {}

    public ResumeDB(Long id, JobSeeker jobSeeker, String name, String type, byte[] data) {
        this.id = id;
        this.jobSeeker = jobSeeker;
        this.name = name;
        this.type = type;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public JobSeeker getJobSeeker() {
        return jobSeeker;
    }

    public void setJobSeeker(JobSeeker jobSeeker) {
        this.jobSeeker = jobSeeker;
    }
}
