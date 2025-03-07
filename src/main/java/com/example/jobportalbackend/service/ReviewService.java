package com.example.jobportalbackend.service;

import com.example.jobportalbackend.model.dto.ReviewDTO;
import com.example.jobportalbackend.model.entity.Employer;
import com.example.jobportalbackend.model.entity.Job;
import com.example.jobportalbackend.model.entity.Review;
import com.example.jobportalbackend.repository.ReviewRepository;
import com.example.jobportalbackend.repository.JobRepository;
import com.example.jobportalbackend.repository.EmployerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        Employer employer = employerRepository.findById(employerId)
                .orElseThrow(() -> new RuntimeException("Employer not found"));

        Review review = new Review(reviewDTO.getRating(), reviewDTO.getComment(), job, employer);
        Review savedReview = reviewRepository.save(review);

        return new ReviewDTO(savedReview.getId(), savedReview.getRating(), savedReview.getComment(), job.getTitle(), employer.getUsername());
    }

    public List<ReviewDTO> getReviewsByJob(Long jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        return reviewRepository.findByJob(job, null)
                .stream()
                .map(review -> new ReviewDTO(review.getId(), review.getRating(), review.getComment(), job.getTitle(), review.getEmployer().getUsername()))
                .collect(Collectors.toList());
    }
}
