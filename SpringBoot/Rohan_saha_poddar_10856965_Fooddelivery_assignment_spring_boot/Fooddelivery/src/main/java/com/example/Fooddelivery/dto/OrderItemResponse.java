package com.example.Fooddelivery.dto;

import java.math.BigDecimal;

public record OrderItemResponse(Long foodItemId, String foodName, Integer quantity, BigDecimal unitPrice,
                                BigDecimal subtotal) {
}
