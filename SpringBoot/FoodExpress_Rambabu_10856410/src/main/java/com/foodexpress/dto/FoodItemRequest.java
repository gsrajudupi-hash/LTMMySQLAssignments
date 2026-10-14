package com.foodexpress.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record FoodItemRequest(@NotBlank String name, String description, @NotNull @DecimalMin("0.0") BigDecimal price,
        @NotBlank String category, boolean available, @NotNull Long restaurantId) {
}
