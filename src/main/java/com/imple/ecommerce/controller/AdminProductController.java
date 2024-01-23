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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/products")
public class AdminProductController {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;

    @PostMapping("/")
    ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest productRequest){

        Product product = productService.createProduct(productRequest);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @DeleteMapping("/{productId}/delete")
    public ResponseEntity<Map<String,Object>> deleteProduct(@PathVariable Long productId)
            throws ProductException {

        productService.deleteProduct(productId);

        Map<String,Object> response = new HashMap<>();
        response.put("message","order deleted succesfully");
        response.put("status",true);

        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Product>> findAllProduct(){
        List<Product> products = productService.findAllProduct();

        return new ResponseEntity<>(products,HttpStatus.OK);
    }
    @PutMapping("/{productId}/update")
    ResponseEntity<Product> updateProduct(@RequestParam Long productId,@RequestBody Product productRequest)
            throws ProductException {

        Product product1 = productService.updateProduct(productId,productRequest);


        return new ResponseEntity<>(product1, HttpStatus.CREATED);
    }

    @PostMapping("/creates")
    ResponseEntity<Map<String,Object>> createMultipleProduct(@RequestBody CreateProductRequest[] productRequestList){

        for (CreateProductRequest request : productRequestList){
            productService.createProduct(request);
        }
        Map<String,Object> response = new HashMap<>();
        response.put("message","Multiple Product added succesfully");
        response.put("status",true);

        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
