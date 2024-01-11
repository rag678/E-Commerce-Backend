package com.imple.ecommerce.service;

import com.imple.ecommerce.exception.ProductException;
import com.imple.ecommerce.model.Review;
import com.imple.ecommerce.model.User;
import com.imple.ecommerce.request.ReviewRequest;

import java.util.List;

public interface ReviewService {

    public Review createReview(ReviewRequest request, User user) throws ProductException;
    public List<Review> getAllReview(Long productId);

}
