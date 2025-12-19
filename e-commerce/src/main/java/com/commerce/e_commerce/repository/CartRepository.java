package com.commerce.e_commerce.repository;

import com.commerce.e_commerce.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {

}
