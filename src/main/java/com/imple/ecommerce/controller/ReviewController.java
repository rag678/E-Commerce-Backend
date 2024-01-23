package com.imple.ecommerce.controller;

import com.imple.ecommerce.exception.ProductException;
import com.imple.ecommerce.exception.UserException;
import com.imple.ecommerce.model.Rating;
import com.imple.ecommerce.model.Review;
import com.imple.ecommerce.model.User;
import com.imple.ecommerce.request.RatingRequest;
import com.imple.ecommerce.request.ReviewRequest;
import com.imple.ecommerce.service.ReviewService;
import com.imple.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Review> createReview(@RequestBody ReviewRequest request
            , @RequestHeader("Authorization") String jwt) throws UserException, ProductException {
        User user = userService.finduserProfileByJwt(jwt);
        Review review = reviewService.createReview(request,user);

        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}")
    ResponseEntity<List<Review>> getProductreview(@PathVariable Long productId
            , @RequestHeader("Authorization") String jwt) throws UserException {

        User user = userService.finduserProfileByJwt(jwt);

        List<Review> reviewList = reviewService.getAllReview(productId);

        return new ResponseEntity<>(reviewList,HttpStatus.OK);
    }
}
