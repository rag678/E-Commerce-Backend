package com.imple.ecommerce.service;

import com.imple.ecommerce.exception.OrderException;
import com.imple.ecommerce.model.Address;
import com.imple.ecommerce.model.Order;
import com.imple.ecommerce.model.User;

import java.util.List;

public interface OrderService {
    public Order createOrder(User user, Address shippingAddress);
    public Order findOrderById(Long orderId) throws OrderException;
    public List<Order> userOrderHistory(Long userId);
    public Order placedOrder(Long orderId) throws OrderException;
    public Order confirmedOrder(Long orderId) throws OrderException;
    public Order shippedOrder(Long orderId) throws OrderException;
    public Order deliveredOrder(Long orderId) throws OrderException;
    public Order canceledOrder(Long orderId) throws OrderException;
    List<Order> getAllOrders();
    public void deleteOrder(Long orderId) throws OrderException;
}
