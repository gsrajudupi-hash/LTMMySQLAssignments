package com.foodexpress.repository;
import com.foodexpress.entity.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;

 import java.util.List;


public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {

 List<FoodItem> findByCategoryIgnoreCase(String category);

 List<FoodItem> findByRestaurant_RestaurantId(Long restaurantId);

 List<FoodItem> findByRestaurant_RestaurantIdAndAvailableTrue(Long restaurantId);

}