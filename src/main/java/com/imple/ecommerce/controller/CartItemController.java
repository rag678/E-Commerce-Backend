package com.imple.ecommerce.controller;

import com.imple.ecommerce.exception.CartItemException;
import com.imple.ecommerce.exception.UserException;
import com.imple.ecommerce.model.CartItem;
import com.imple.ecommerce.model.User;
import com.imple.ecommerce.service.CartItemService;
import com.imple.ecommerce.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/cart_items")
public class CartItemController {

    @Autowired
    private UserService userService;

    @Autowired
    private CartItemService cartItemService;

    @DeleteMapping("/{cartItemId}")
    @Operation(description = "Remove Cart Item from Cart")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Delete Item")
    public ResponseEntity<Map<String,Object>> deleteCartItem(@PathVariable Long cartItemId,
                                                      @RequestHeader("Authorization")String jwt) throws UserException, CartItemException {
        User user = userService.finduserProfileByJwt(jwt);
        cartItemService.removeCartItem(user.getId(), cartItemId);

//        ApiResponse res = new ApiResponse();
        Map<String,Object> map = new HashMap<>();
        map.put("message","Item Removed from cart");
        map.put("status",true);


        return ResponseEntity.ok(map);
    }

    @PutMapping("/{cartItemId}")
    @Operation(description = "Update Item to Cart")
    public ResponseEntity<CartItem> updateCartItem(@RequestBody CartItem cartItem,@PathVariable Long cartItemId,
                                                   @RequestHeader("Authorization")String jwt) throws UserException, CartItemException{
        User user = userService.finduserProfileByJwt(jwt);
        CartItem cartItem1 = cartItemService.updateCartItem(user.getId(), cartItemId,cartItem);

        return new ResponseEntity<>(cartItem1, HttpStatus.ACCEPTED);
    }
}
