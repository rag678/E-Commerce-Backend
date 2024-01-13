package com.imple.ecommerce.service;

import com.imple.ecommerce.model.OrderItem;
import com.imple.ecommerce.repository.OrderItemRepository;

public class OrderItemServiceImplementation implements OrderItemService{
    private OrderItemRepository orderItemRepository;

    public OrderItemServiceImplementation(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public OrderItem createOrder(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }
}
