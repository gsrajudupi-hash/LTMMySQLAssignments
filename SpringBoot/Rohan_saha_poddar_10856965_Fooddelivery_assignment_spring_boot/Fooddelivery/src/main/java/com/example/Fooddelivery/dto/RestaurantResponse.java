package com.example.Fooddelivery.dto;

public record RestaurantResponse(Long restaurantId, String name, String location, String contactNumber, boolean active) {
}
