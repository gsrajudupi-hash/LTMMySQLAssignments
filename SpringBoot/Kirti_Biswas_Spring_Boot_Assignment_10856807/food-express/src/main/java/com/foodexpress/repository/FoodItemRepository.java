package com.foodexpress.repository;

import com.foodexpress.entity.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {

    List<FoodItem> findByCategory(String category);

    List<FoodItem> findByRestaurantRestaurantId(Long restaurantId);

    List<FoodItem> findByRestaurantRestaurantIdAndAvailableTrue(Long restaurantId);

    List<FoodItem> findByPriceBetween(BigDecimal minPrice,
                                      BigDecimal maxPrice);
}