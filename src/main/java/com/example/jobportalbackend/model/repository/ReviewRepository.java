package com.example.jobportalbackend.repository;

import com.example.jobportalbackend.model.entity.Job;
import com.example.jobportalbackend.model.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // ✅ Get all reviews for a job
    Page<Review> findByJob(Job job, Pageable pageable);

    // ✅ Get reviews filtered by rating
    Page<Review> findByJobAndRatingGreaterThanEqual(Job job, int minRating, Pageable pageable);
}
