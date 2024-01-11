package com.imple.ecommerce.service;

import com.imple.ecommerce.exception.ProductException;
import com.imple.ecommerce.model.Rating;
import com.imple.ecommerce.model.User;
import com.imple.ecommerce.request.RatingRequest;

import java.util.List;

public interface RatingService {

    public Rating createRating(RatingRequest request, User user) throws ProductException;
    public List<Rating> getProductsRating(Long productId);
}
