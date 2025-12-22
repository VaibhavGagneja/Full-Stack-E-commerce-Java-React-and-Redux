package com.commerce.e_commerce.service;

import com.commerce.e_commerce.domain.OrderStatus;
import com.commerce.e_commerce.model.*;

import java.util.*;

public interface OrderService {
    Set<Order> createOrder(User user, Address shippingAddress, Cart cart) throws Exception;
    Order findOrderById(long id) throws Exception;
    List<Order> usersOrderHistory(Long userId); 
    List<Order> sellersOrder(Long sellerId); 
    Order updateOrderStatus(Long orderId, OrderStatus orderStatus) throws Exception;
    Order cancelOrder(Long orderId, User user) throws Exception;
    OrderItem getOrderItemById(Long id) throws Exception;
}
