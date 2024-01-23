package com.imple.ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne
//    @JoinColumn(name = "cart_id")
//    private Cart cart;

    @ManyToOne
    private Product product;
    private String size;

    private int quantity;
    private Integer price;
    private Integer discountedPrice;
    private Long userId;
}
