package com.foodexpress.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record CreateOrderRequest(@NotNull Long customerId, @NotNull Long restaurantId,
                                 @NotNull Long addressId, @NotEmpty List<OrderItemRequest> items) {
}
