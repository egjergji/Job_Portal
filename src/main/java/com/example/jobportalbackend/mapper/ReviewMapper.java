package com.example.jobportalbackend.mapper;

import com.example.jobportalbackend.model.dto.ReviewDTO;
import com.example.jobportalbackend.model.entity.Review;

public class ReviewMapper extends AbstractMapper<Review, ReviewDTO> {

    @Override
    public Review toEntity(ReviewDTO reviewDTO) {
        Review review = new Review();
        review.setId(reviewDTO.getId());
        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());
        review.setJob(reviewDTO.getJob());
        review.setEmployer(reviewDTO.getEmployer());
        return review;
    }

    @Override
    public ReviewDTO toDto(Review review) {
        if (review == null) {
            return null;
        }
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setId(review.getId());
        reviewDTO.setRating(review.getRating());
        reviewDTO.setComment(review.getComment());
        reviewDTO.setJob(review.getJob());
        reviewDTO.setEmployer(review.getEmployer());
        return reviewDTO;
    }
}
