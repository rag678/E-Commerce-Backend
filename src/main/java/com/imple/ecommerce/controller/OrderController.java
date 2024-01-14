package com.imple.ecommerce.controller;

import com.imple.ecommerce.exception.OrderException;
import com.imple.ecommerce.exception.UserException;
import com.imple.ecommerce.model.Address;
import com.imple.ecommerce.model.Order;
import com.imple.ecommerce.model.User;
import com.imple.ecommerce.service.OrderService;
import com.imple.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<Order> createOrder(@RequestBody Address shippingAddress,
                                             @RequestHeader("Authorization") String jwt) throws UserException{
        User user = userService.finduserProfileByJwt(jwt);

        Order order = orderService.createOrder(user,shippingAddress);

        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Order>> userOrderHistory(@RequestHeader("Authorization")String jwt) throws UserException {
        User user = userService.finduserProfileByJwt(jwt);

        List<Order> orders = orderService.userOrderHistory(user.getId());

        return new ResponseEntity<>(orders,HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> findOrderById(@PathVariable("id") Long orderId,
                                               @RequestHeader("Authorization") String jwt) throws UserException, OrderException{
        User user = userService.finduserProfileByJwt(jwt);
        Order order = orderService.findOrderById(orderId);

        return new ResponseEntity<>(order,HttpStatus.CREATED);
    }
}
