package com.imple.ecommerce.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeControl {
    @GetMapping("/home")
    public String home(){
        return "Backend is Working fine";
    }
}
