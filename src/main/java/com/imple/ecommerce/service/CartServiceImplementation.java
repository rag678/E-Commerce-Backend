package com.imple.ecommerce.service;

import com.imple.ecommerce.exception.ProductException;
import com.imple.ecommerce.exception.UserException;
import com.imple.ecommerce.model.Cart;
import com.imple.ecommerce.model.CartItem;
import com.imple.ecommerce.model.Product;
import com.imple.ecommerce.model.User;
import com.imple.ecommerce.repository.CartItemRepository;
import com.imple.ecommerce.repository.CartRepository;
import com.imple.ecommerce.request.AddItemRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class CartServiceImplementation implements CartService {

    private CartRepository cartRepository;
    private CartItemService cartItemService;
    private ProductService productService;
    private UserService userService;
    @Autowired
    private CartItemRepository cartItemRepository;

    public CartServiceImplementation(CartRepository cartRepository, CartItemService cartItemService,
                                     ProductService productService) {
        this.cartRepository = cartRepository;
        this.cartItemService = cartItemService;
        this.productService = productService;
    }

    @Override
    public Cart createCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    @Override
    public String addCartItem(Long userId, AddItemRequest request) throws ProductException {
        Cart cart = cartRepository.findByUserId(userId);
        Product product = productService.findProductById(request.getProductId());
        CartItem isItemPresent = cartItemService.isCartItemExist(cart,product, request.getSize(), userId);

        if (isItemPresent == null){
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
//            cartItem.setCart(cart);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setUserId(userId);

            int price = request.getQuantity() * product.getDiscountedPrice();
            cartItem.setPrice(price);
            cartItem.setSize(request.getSize());

            CartItem createdCartItem = cartItemService.createCartItem(cartItem);
            Set<CartItem> cartCartItems = cart.getCartItems();
            cartCartItems.add(createdCartItem);
            cartRepository.save(cart);
//            addCartItemToCart(createdCartItem, cart.getId());
//            log.info("cart after set cartItem added :{}",cart);
//            cart.getCartItems().add(createdCartItem);
//            log.info("cart after set cartItem added :{}",cart);
//            Cart cart1 = cartRepository.save(cart);
        }
        return "Item Added to Cart";
    }

    @Override
    public Cart findUserCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId);

        int totalPrice = 0;
        int totalDiscountedPrice = 0;
        int totalItem = 0;

        List<CartItem> cartItemList = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItems()) {
            cartItemList.add(cartItem);
            totalPrice += cartItem.getPrice();
            totalDiscountedPrice += cartItem.getDiscountedPrice();
            totalItem += cartItem.getQuantity();
        }

        cart.setTotalDiscountedPrice(totalDiscountedPrice);
        cart.setTotalItem(totalItem);
        cart.setTotalPrice(totalPrice);
        cart.setDiscount(totalPrice-totalDiscountedPrice);
        Cart cart1 = cartRepository.save(cart);
        Set<CartItem> cartItemSet = new HashSet<>(cartItemList);
        cart1.setCartItems(cartItemSet);
        log.info("cartItem size : {}",cartItemList.size());
        return cart1;
    }
}
