package com.commerce.e_commerce.service;

import com.commerce.e_commerce.model.Product;
import com.commerce.e_commerce.model.Review;
import com.commerce.e_commerce.model.User;
import com.commerce.e_commerce.request.CreateReviewRequest;

import java.util.List;

public interface ReviewService {
    Review createReview(CreateReviewRequest req, User user, Product product);

    List<Review> getReviewByProductId(Long productId);

    Review updateReview(Long reviewId, String reviewText, double rating, Long userId) throws Exception;

    void deleteReview(Long reviewId, Long userId) throws Exception;
    Review getReviewById(Long reviewId) throws Exception;
}
