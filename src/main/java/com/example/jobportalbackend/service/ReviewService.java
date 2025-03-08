package com.example.jobportalbackend.service;

import com.example.jobportalbackend.model.dto.ReviewDTO;
import com.example.jobportalbackend.model.entity.Job;
import com.example.jobportalbackend.model.entity.Review;
import com.example.jobportalbackend.repository.EmployerRepository;
import com.example.jobportalbackend.repository.JobRepository;
import com.example.jobportalbackend.repository.ReviewRepository;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final JobRepository jobRepository;
    private final EmployerRepository employerRepository;

    public ReviewService(ReviewRepository reviewRepository, JobRepository jobRepository, EmployerRepository employerRepository) {
        this.reviewRepository = reviewRepository;
        this.jobRepository = jobRepository;
        this.employerRepository = employerRepository;
    }

    public ReviewDTO addReview(Long jobId, Long employerId, ReviewDTO reviewDTO) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        if (!job.getEmployer().getId().equals(employerId)) {
            throw new RuntimeException("Unauthorized: Only the employer who posted this job can add reviews.");
        }

        Review review = new Review(reviewDTO.getRating(), reviewDTO.getComment(), job, job.getEmployer());
        Review savedReview = reviewRepository.save(review);

        return new ReviewDTO(savedReview.getRating(), savedReview.getComment(), job, job.getEmployer());
    }
}
