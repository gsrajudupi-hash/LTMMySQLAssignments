package com.example.Fooddelivery.dto;

import com.example.Fooddelivery.entities.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(Long orderId, Long customerId, Long restaurantId, Long addressId, LocalDateTime orderDate, OrderStatus status,
                            BigDecimal totalAmount, List<OrderItemResponse> items) {
}
