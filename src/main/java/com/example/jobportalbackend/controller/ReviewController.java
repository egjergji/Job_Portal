package com.example.jobportalbackend.controller;

import com.example.jobportalbackend.model.dto.ReviewDTO;
import com.example.jobportalbackend.service.ReviewService;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/job/{jobId}")
    public List<ReviewDTO> getReviewsForJob(
            @PathVariable Long jobId,
            @RequestParam(required = false) Integer minRating,
            @RequestParam(defaultValue = "0") int page) {
        return reviewService.getReviewsForJob(jobId, minRating, PageRequest.of(page, 10)).getContent();
    }

}
