package com.foodexpress.dto;

import jakarta.validation.constraints.*;

public record OrderItemRequest(@NotNull Long foodItemId, @Min(1) int quantity) {
}
