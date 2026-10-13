package com.foodexpress.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {

    private Long foodItemId;

    private String foodName;

    private Integer quantity;

    private BigDecimal unitPrice;

    private BigDecimal subtotal;
}