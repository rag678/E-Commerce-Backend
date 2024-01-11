package com.imple.ecommerce.service;

import com.imple.ecommerce.exception.ProductException;
import com.imple.ecommerce.model.Product;
import com.imple.ecommerce.model.Review;
import com.imple.ecommerce.model.User;
import com.imple.ecommerce.repository.ProductRepository;
import com.imple.ecommerce.repository.RatingRepository;
import com.imple.ecommerce.repository.ReviewRepository;
import com.imple.ecommerce.request.ReviewRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewServiceImplementation implements ReviewService{

    private ReviewRepository reviewRepository;
    private ProductService productService;
    private ProductRepository productRepository;

    public ReviewServiceImplementation(ReviewRepository reviewRepository, ProductService productService,
                                       ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.productService = productService;
        this.productRepository = productRepository;
    }

    @Override
    public Review createReview(ReviewRequest request, User user) throws ProductException {
        Product product = productService.findProductById(request.getProductId());

        Review review = new Review();
        review.setUser(user);
        review.setReview(review.getReview());
        review.setProduct(product);
        review.setCreatedAt(LocalDateTime.now());

        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getAllReview(Long productId) {
        return reviewRepository.getAllProductsReview(productId);
    }
}
