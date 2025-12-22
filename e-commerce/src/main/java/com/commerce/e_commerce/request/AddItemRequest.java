package com.commerce.e_commerce.request;

import com.commerce.e_commerce.model.Product;
import lombok.Data;

@Data
public class AddItemRequest {
    private String size;
    private int quantity;
    private Long productId;
}
