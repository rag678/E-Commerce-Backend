package com.imple.ecommerce.repository;

import com.imple.ecommerce.model.Cart;
import com.imple.ecommerce.model.CartItem;
import com.imple.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    @Query("SELECT ci From CartItem ci WHERE ci.product=:product AND ci.size = :size AND"+
    " ci.userId =:userId")
    CartItem isCartItemExist(@Param("product") Product product,@Param("size") String size,
                             @Param("userId") Long userId);
//    @Query("SELECT ci From CartItem ci WHERE ci.cart=:cart AND ci.product=:product AND ci.size = :size AND"+
//    " ci.userId =:userId")
//    CartItem isCartItemExist(@Param("cart") Cart cart,@Param("product") Product product,@Param("size") String size,
//                             @Param("userId") Long userId);
}
