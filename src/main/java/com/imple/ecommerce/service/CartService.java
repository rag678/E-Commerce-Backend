package com.imple.ecommerce.service;

import com.imple.ecommerce.exception.ProductException;
import com.imple.ecommerce.model.Cart;
import com.imple.ecommerce.model.User;
import com.imple.ecommerce.request.AddItemRequest;

public interface CartService {

    public Cart createCart(User user);

    public String addCartItem(Long userId, AddItemRequest request)throws ProductException;

    public Cart findUserCart(Long userId);
}
