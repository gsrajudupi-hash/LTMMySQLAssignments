package com.example.Fooddelivery.repository;

import com.example.Fooddelivery.entities.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodItemRepository extends JpaRepository<FoodItem ,Long> {
}
