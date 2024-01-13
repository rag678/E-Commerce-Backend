package com.imple.ecommerce.utils;

import lombok.Data;
import org.springframework.stereotype.Service;

@Data
public abstract class ApiResponse implements io.swagger.v3.oas.annotations.responses.ApiResponse {
    private String status;
    private String message;
}
