package com.foodexpress.repository;

import com.foodexpress.entity.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

//# Repository interface for managing FoodItem entities
public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {
    //# Custom implementation for finding FoodItems by category (case-insensitive)
    List<FoodItem> findByCategoryIgnoreCase(String category);

    //# Custom implementation for finding available FoodItems by restaurant ID
    List<FoodItem> findByRestaurantRestaurantIdAndAvailableTrue(Long id);
}
