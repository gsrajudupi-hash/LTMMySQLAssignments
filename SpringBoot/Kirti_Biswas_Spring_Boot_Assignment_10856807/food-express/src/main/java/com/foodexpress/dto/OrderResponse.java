package com.foodexpress.dto;

import com.foodexpress.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private Long orderId;

    private LocalDateTime orderDate;

    private OrderStatus status;

    private BigDecimal totalAmount;

    private Long customerId;

    private String customerName;

    private Long restaurantId;

    private String restaurantName;

    private Long addressId;

    private String deliveryAddress;

    private List<OrderItemResponse> items;
}