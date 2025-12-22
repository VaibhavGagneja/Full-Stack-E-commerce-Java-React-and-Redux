package com.commerce.e_commerce.service;

import com.commerce.e_commerce.model.Cart;
import com.commerce.e_commerce.model.CartItem;
import com.commerce.e_commerce.model.Product;
import com.commerce.e_commerce.model.User;

public interface CartService {
    public CartItem addCartItem(
            User user,
            Product product,
            String size,
            int quantity
    );

    public Cart findUsedCart(User user);
}
