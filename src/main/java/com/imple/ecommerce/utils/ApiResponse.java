package com.imple.ecommerce.utils;

import lombok.Data;
import org.springframework.stereotype.Service;

@Data
public class ApiResponse  {
    private String status;
    private String message;
}
