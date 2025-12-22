package com.commerce.e_commerce.service;

import com.commerce.e_commerce.model.Order;
import com.commerce.e_commerce.model.PaymentOrder;
import com.commerce.e_commerce.model.User;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayException;

import java.util.Set;

public interface PaymentService {
    PaymentOrder createOrder(User user, Set<Order> orders);

    PaymentOrder getPaymentOrderById(Long orderId) throws  Exception;

    PaymentOrder getPaymentOrderByPaymentId(String orderId) throws Exception;

    Boolean ProceedPaymentOrder(PaymentOrder paymentOrder, String paymentId, String paymentLinkId) throws RazorpayException;

    PaymentLink createRazorpayPaymentLink(User user, Long amount, Long orderId);

    String createStripePaymentLink(User user, Long amount, Long order);
}
