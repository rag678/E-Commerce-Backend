package com.imple.ecommerce.controller;

import com.imple.ecommerce.exception.UserException;
import com.imple.ecommerce.model.User;
import com.imple.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.finduserProfileByJwt(jwt);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
