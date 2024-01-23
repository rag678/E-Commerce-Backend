package com.imple.ecommerce.service;

import com.imple.ecommerce.exception.OrderException;
import com.imple.ecommerce.model.*;
import com.imple.ecommerce.repository.*;
import com.imple.ecommerce.utils.OrderStatus;
import com.imple.ecommerce.utils.PaymentStatus;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrderServiceImplementation implements OrderService{
    private Orderrepository orderrepository;
    private CartService cartService;
    private AddressRepository addressRepository;
    private UserRepository userRepository;
    private OrderItemRepository orderItemRepository;

    public OrderServiceImplementation(
            Orderrepository orderrepository, CartService cartService,AddressRepository addressRepository,
            UserRepository userRepository, OrderItemRepository orderItemRepository) {
        this.orderrepository = orderrepository;
        this.cartService = cartService;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    @Transactional
    public Order createOrder(User user, Address shippingAddress) {
        shippingAddress.setUser(user);
        Address address = addressRepository.save(shippingAddress);
        user.getAddresses().add(address);
        userRepository.save(user);

        Cart cart = cartService.findUserCart(user.getId());
        Set<OrderItem> orderItemList = new HashSet<>();
        Order createdOrder = new Order();

        for (CartItem cartItem : cart.getCartItems()){
            OrderItem orderItem = new OrderItem();
            orderItem.setPrice(cartItem.getPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setSize(cartItem.getSize());
            orderItem.setUserId(cartItem.getUserId().longValue());
            orderItem.setDiscountedPrice(cartItem.getDiscountedPrice());

            OrderItem createdOrderItem = orderItemRepository.save(orderItem);

            orderItemList.add(createdOrderItem);
        }

        createdOrder.setCreatedAt(LocalDateTime.now());
        createdOrder.setUser(user);
        createdOrder.setOrderItems(orderItemList);
        createdOrder.setTotalPrice(cart.getTotalPrice());
        createdOrder.setTotalDiscountedPrice(cart.getTotalDiscountedPrice());
        createdOrder.setDiscount(cart.getDiscount());
        createdOrder.setTotalItem(cart.getTotalItem());

        createdOrder.setShippingAddress(address);
        createdOrder.setOrderDate(LocalDateTime.now());
        createdOrder.getPaymentDetails().setStatus(PaymentStatus.PENDING);
        createdOrder.setOrderStatus(OrderStatus.PENDING);

        Order savedOrder = orderrepository.save(createdOrder);

//        for (OrderItem item : orderItemList){
//            item.setOrder(savedOrder);
//            orderItemRepository.save(item);
//        }
        return savedOrder;
    }

    @Override
    public Order findOrderById(Long orderId) throws OrderException {
        Optional<Order> optionalOrder = orderrepository.findById(orderId);

        if (optionalOrder.isPresent()) return optionalOrder.get();

        throw new OrderException("Order Not Found by id - "+ orderId);
    }

    @Override
    public List<Order> userOrderHistory(Long userId) {

        return orderrepository.getUserOrder(userId);
    }

    @Override
    public Order placedOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus(OrderStatus.PLACED);
        order.getPaymentDetails().setStatus(PaymentStatus.COMPLETED);
        return order;
    }

    @Override
    public Order confirmedOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus(OrderStatus.CONFIRMED);


        return orderrepository.save(order);
    }

    @Override
    public Order shippedOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus(OrderStatus.SHIPPED);
        return orderrepository.save(order);
    }

    @Override
    public Order deliveredOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus(OrderStatus.DELIVERED);
        return orderrepository.save(order);
    }

    @Override
    public Order canceledOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus(OrderStatus.CANCELLED);
        return orderrepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderrepository.findAll();
    }

    @Override
    public void deleteOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);

        orderrepository.delete(order);
    }

}
