package com.imple.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    private Order order;

    @ManyToOne
    private Product product;

    private String size;
    private int quantity;
    private Integer price;
    private Integer discountedPrice;
    private Long userId;
    private LocalDateTime deliveryDate;

    public OrderItem() {
    }

    public OrderItem(Long id, Order order, Product product, String size, int quantity, Integer price,
                     Integer discountedPrice, Long userId, LocalDateTime deliveryDate) {
        this.id = id;
        this.order = order;
        this.product = product;
        this.size = size;
        this.quantity = quantity;
        this.price = price;
        this.discountedPrice = discountedPrice;
        this.userId = userId;
        this.deliveryDate = deliveryDate;
    }
}
