package com.commerce.e_commerce.service.impl;

import com.commerce.e_commerce.model.Product;
import com.commerce.e_commerce.model.Review;
import com.commerce.e_commerce.model.User;
import com.commerce.e_commerce.repository.ReviewRepository;
import com.commerce.e_commerce.request.CreateReviewRequest;
import com.commerce.e_commerce.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Override
    public Review createReview(CreateReviewRequest req, User user, Product product) {
        Review review = new Review();
        review.setUser(user);
        review. setProduct(product);
        review. setReviewText(req.getReviewText());
        review. setRating(req.getReviewRating());
        review. setProductImages(req.getProductImages());

        product.getReviews().add(review);
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getReviewByProductId(Long productId) {
        return reviewRepository.findByProductId(productId);
    }

    @Override
    public Review updateReview(Long reviewId, String reviewText, double rating, Long userId) throws Exception {
        Review review = getReviewById(reviewId);
        if(review.getUser().getId().equals(userId)) {
            review.setReviewText(reviewText);
            review.setRating(rating);
            return reviewRepository.save(review);
        }
        throw new Exception("You Cant Update this Review");
    }

    @Override
    public void deleteReview(Long reviewId, Long userId) throws Exception{
        Review review = getReviewById(reviewId);
        if (review.getUser().getId().equals(userId)) {
            throw new Exception("you can't delete this review");
        }

    }

    @Override
    public Review getReviewById(Long reviewId) throws Exception {
        return reviewRepository.findById(reviewId).orElseThrow(()->
                new Exception("review not found")
        );
    }
}
