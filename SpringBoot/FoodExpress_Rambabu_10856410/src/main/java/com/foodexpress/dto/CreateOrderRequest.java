package com.foodexpress.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;

public record CreateOrderRequest(@NotNull Long customerId, @NotNull Long restaurantId, @NotNull Long addressId,
        @NotEmpty List<@Valid OrderItemRequest> items) {
}
