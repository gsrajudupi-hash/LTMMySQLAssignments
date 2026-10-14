package com.example.Fooddelivery.dto;

import java.math.BigDecimal;

public record FoodItemRequest(String name, String description, BigDecimal price, String category, boolean available, Long restaurantId) {
}
