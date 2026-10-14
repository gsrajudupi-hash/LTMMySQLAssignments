package com.example.Fooddelivery.dto;

import java.math.BigDecimal;

public record FoodItemResponse(Long foodItemId, String name, String description, BigDecimal price, String category, boolean available, Long restaurantId) {
}
