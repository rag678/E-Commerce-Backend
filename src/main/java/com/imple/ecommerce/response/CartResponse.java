package com.imple.ecommerce.response;

import com.imple.ecommerce.model.CartItem;
import com.imple.ecommerce.model.User;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
@Builder
public class CartResponse {
    private Long id;
    private User user;
    private List<CartItem> cartItems = new ArrayList<>();
    private double totalPrice;
    private int totalItem;
    private int totalDiscountedPrice;
    private int discount;
}
