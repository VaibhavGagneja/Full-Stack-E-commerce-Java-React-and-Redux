package com.commerce.e_commerce.service;

import com.commerce.e_commerce.model.Product;
import com.commerce.e_commerce.model.User;
import com.commerce.e_commerce.model.Wishlist;

public interface WishListService {
        Wishlist createWishlist(User user);
        Wishlist getWishlistByUserId(User user); 
        Wishlist addProductToWishlist(User user, Product product);
}
