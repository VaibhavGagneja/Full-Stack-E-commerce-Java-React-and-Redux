package com.commerce.e_commerce.repository;

import com.commerce.e_commerce.model.Cart;
import com.commerce.e_commerce.model.CartItem;
import com.commerce.e_commerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {

    CartItem findByCartAndProductAndSize(Cart cart, Product product, String size);

}
