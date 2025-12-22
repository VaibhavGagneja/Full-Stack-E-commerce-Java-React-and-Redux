package com.commerce.e_commerce.service;

import com.commerce.e_commerce.model.Order;
import com.commerce.e_commerce.model.Seller;
import com.commerce.e_commerce.model.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction createTransaction(Order order);
    List<Transaction> getTransactionsBySellerId(Seller seller);
    List<Transaction> getAllTransactions(); 
}
