package com.imple.ecommerce.controller;

import com.imple.ecommerce.exception.ProductException;
import com.imple.ecommerce.exception.UserException;
import com.imple.ecommerce.model.Rating;
import com.imple.ecommerce.model.User;
import com.imple.ecommerce.request.RatingRequest;
import com.imple.ecommerce.service.RatingService;
import com.imple.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    @Autowired
    private UserService userService;
    @Autowired
    private RatingService ratingService;

    @PostMapping("/create")
    public ResponseEntity<Rating> createRating(@RequestBody RatingRequest request
    , @RequestHeader("Authorization") String jwt) throws UserException, ProductException {
            User user = userService.finduserProfileByJwt(jwt);
            Rating rating = ratingService.createRating(request,user);

            return new ResponseEntity<>(rating, HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}")
    ResponseEntity<List<Rating>> getProductRating(@PathVariable Long productId
            , @RequestHeader("Authorization") String jwt) throws UserException {

        User user = userService.finduserProfileByJwt(jwt);

        List<Rating> ratingList = ratingService.getProductsRating(productId);

        return new ResponseEntity<>(ratingList,HttpStatus.OK);
    }

}
