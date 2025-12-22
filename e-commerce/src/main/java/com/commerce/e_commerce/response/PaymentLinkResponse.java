package com.commerce.e_commerce.response;

import com.commerce.e_commerce.model.OrderItem;
import lombok.Data;

@Data
public class PaymentLinkResponse {
    private String payment_link_url;
    private OrderItem payment_link_id;
}
