package com.imple.ecommerce.controller;

import com.imple.ecommerce.exception.ProductException;
import com.imple.ecommerce.exception.UserException;
import com.imple.ecommerce.model.Cart;
import com.imple.ecommerce.model.User;
import com.imple.ecommerce.request.AddItemRequest;
import com.imple.ecommerce.service.CartService;
import com.imple.ecommerce.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;

    @GetMapping("/")
    @Operation(description = "find cart by userId")
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization")String jwt) throws UserException{
        User user = userService.finduserProfileByJwt(jwt);
        Cart cart = cartService.findUserCart(user.getId());

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }



    @PutMapping("/add")
    public ResponseEntity<?> addItemToCart(@RequestBody AddItemRequest itemRequest,
                                           @RequestHeader("Authorization") String jwt)throws UserException, ProductException{
        User user = userService.finduserProfileByJwt(jwt);
//        AddItemRequest itemRequest1 = AddItemRequest.builder().size(size).productId(productId).build();
        log.info("productId + {}",itemRequest.getProductId());
        cartService.addCartItem(user.getId(), itemRequest);

        return new ResponseEntity<>("Item Added to Cart",HttpStatus.OK);
    }
}
