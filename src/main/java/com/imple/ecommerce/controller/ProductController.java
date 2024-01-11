package com.imple.ecommerce.controller;

import com.imple.ecommerce.exception.ProductException;
import com.imple.ecommerce.model.Product;
import com.imple.ecommerce.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class ProductController {

    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<Page<Product>> findProductByCategoryHandler(@RequestParam String category,@RequestParam List<String> color,
                                                                      @RequestParam List<String> size,@RequestParam Integer minPrice,
                                                                      @RequestParam Integer maxPrice,@RequestParam Integer minDiscount,
                                                                      @RequestParam String sort,@RequestParam String stock,
                                                                      @RequestParam Integer pageNumber,@RequestParam Integer pageSize){
        Page<Product> response = productService.getAllProduct(category, color, size, minPrice, maxPrice, minDiscount, sort, stock, pageNumber, pageSize);

        log.info("Complete products");
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/products/id/{productId}")
    public ResponseEntity<Product> findProductByIdHandler(@PathVariable Long productId) throws ProductException {
        Product product = productService.findProductById(productId);

        return new ResponseEntity<>(product,HttpStatus.ACCEPTED);
    }

//    @GetMapping("/products/search")
//    public ResponseEntity<List<Product>> searchProductHandler(@RequestParam String q){
//
//        List<Product> products = productService.searchProduct(q);
//
//        return new ResponseEntity<>(products,HttpStatus.OK);
//    }

}
