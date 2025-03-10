package com.example.jobportalbackend.service;

import com.example.jobportalbackend.exception.ResourceNotFoundException;
import com.example.jobportalbackend.exception.UnauthorizedActionException;
import com.example.jobportalbackend.mapper.ReviewMapper;
import com.example.jobportalbackend.model.dto.ReviewDTO;
import com.example.jobportalbackend.model.entity.Job;
import com.example.jobportalbackend.model.entity.Review;
import com.example.jobportalbackend.repository.JobRepository;
import com.example.jobportalbackend.repository.ReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final JobRepository jobRepository;
    private final ReviewMapper reviewMapper;

    public ReviewService(ReviewRepository reviewRepository, JobRepository jobRepository,
                          ReviewMapper reviewMapper) {
        this.reviewRepository = reviewRepository;
        this.jobRepository = jobRepository;
        this.reviewMapper = reviewMapper;
    }

    public Page<ReviewDTO> getReviewsForJob(Long jobId, Integer minRating, Pageable pageable) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job with ID " + jobId + " not found"));

        Page<Review> reviews = (minRating != null)
                ? reviewRepository.findByJobAndRatingGreaterThanEqual(job, minRating, pageable)
                : reviewRepository.findByJob(job, pageable);

        return reviews.map(reviewMapper::toDto);
    }

    public ReviewDTO addReview(Long jobId, Long employerId, ReviewDTO reviewDTO) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job with ID " + jobId + " not found"));

        if (!job.getEmployer().getId().equals(employerId)) {
            throw new UnauthorizedActionException("Unauthorized: Only the employer who posted this job can add reviews.");
        }

        Review review = reviewMapper.toEntity(reviewDTO);
        review.setJob(job);
        review.setEmployer(job.getEmployer());

        Review savedReview = reviewRepository.save(review);
        return reviewMapper.toDto(savedReview);
    }
}
