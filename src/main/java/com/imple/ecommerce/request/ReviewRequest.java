package com.imple.ecommerce.request;

import lombok.Data;

@Data
public class ReviewRequest {
    private Long productId;
    private double review;
}
