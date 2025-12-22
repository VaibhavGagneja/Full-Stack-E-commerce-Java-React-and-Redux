package com.commerce.e_commerce.service.impl;

import com.commerce.e_commerce.model.Order;
import com.commerce.e_commerce.model.Seller;
import com.commerce.e_commerce.model.Transaction;
import com.commerce.e_commerce.repository.SellerRepository;
import com.commerce.e_commerce.repository.TransactionRepository;
import com.commerce.e_commerce.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {


    private final SellerRepository sellerRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public Transaction createTransaction(Order order) {
        Seller seller = sellerRepository.findById(order.getSellerId()).get();

        Transaction transaction = new Transaction();
        transaction. setSeller(seller);
        transaction. setCustomer(order.getUser());
        transaction.setOrder(order);

        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getTransactionsBySellerId(Seller seller) {
        return transactionRepository.findBySellerId(seller.getId());
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}
