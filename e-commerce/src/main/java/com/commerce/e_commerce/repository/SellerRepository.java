package com.commerce.e_commerce.repository;

import com.commerce.e_commerce.domain.AccountStatus;
import com.commerce.e_commerce.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SellerRepository extends JpaRepository<Seller, Long> {
    Seller findByEmail(String email);
    List<Seller> findByAccountStatus(AccountStatus status);
}
