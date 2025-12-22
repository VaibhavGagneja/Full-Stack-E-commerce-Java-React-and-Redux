package com.commerce.e_commerce.service;

import com.commerce.e_commerce.domain.AccountStatus;
import com.commerce.e_commerce.exception.SellerException;
import com.commerce.e_commerce.model.Seller;

import java.util.List;

public interface SellerService {
    Seller getSellerProfile(String jwt) throws Exception;

    Seller createSeller(Seller seller) throws  Exception;

    Seller getSellerById(Long id) throws SellerException;

    Seller getSellerByEmail(String email) throws SellerException;

    List<Seller> getAllSellers(AccountStatus status) throws SellerException;

    Seller updateSeller(Long id, Seller seller) throws Exception;

    void deleteSeller(Long id) throws Exception;

    Seller verifyEmail(String email, String otp) throws Exception;

    Seller updateSellerAccountStatus(Long sellerId, AccountStatus status) throws Exception;
}
