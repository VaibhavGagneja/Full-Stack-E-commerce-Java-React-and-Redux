package com.commerce.e_commerce.repository;

import com.commerce.e_commerce.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListRepository extends JpaRepository<Wishlist,Long> {
    Wishlist findByUserId(Long userId);
}
