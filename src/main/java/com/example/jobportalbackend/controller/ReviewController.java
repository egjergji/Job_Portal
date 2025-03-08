package com.example.jobportalbackend.controller;

import com.example.jobportalbackend.model.dto.ReviewDTO;
import com.example.jobportalbackend.service.ReviewService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ReviewDTO addReview(@RequestParam Long jobId, @RequestParam Long employerId, @RequestBody ReviewDTO reviewDTO) {
        return reviewService.addReview(jobId, employerId, reviewDTO);
    }

   /* @GetMapping("/{jobId}")
    public List<ReviewDTO> getReviewsByJob(@PathVariable Long jobId) {
        return reviewService.getReviewsByJob(jobId);
    }*/
}
