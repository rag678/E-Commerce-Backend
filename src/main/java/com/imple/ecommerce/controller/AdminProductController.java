package com.imple.ecommerce.controller;

import com.imple.ecommerce.exception.ProductException;
import com.imple.ecommerce.model.Product;
import com.imple.ecommerce.repository.ProductRepository;
import com.imple.ecommerce.request.CreateProductRequest;
import com.imple.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/admin")
public class AdminProductController {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;

    @PostMapping("/products")
    ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest productRequest){

        Product product1 = productService.createProduct(productRequest);


        return new ResponseEntity<>(product1, HttpStatus.CREATED);
    }
    @PutMapping("/products")
    ResponseEntity<Product> updateProduct(@RequestParam Long productId,@RequestBody Product productRequest) throws ProductException {

        Product product1 = productService.updateProduct(productId,productRequest);


        return new ResponseEntity<>(product1, HttpStatus.CREATED);
    }
}
