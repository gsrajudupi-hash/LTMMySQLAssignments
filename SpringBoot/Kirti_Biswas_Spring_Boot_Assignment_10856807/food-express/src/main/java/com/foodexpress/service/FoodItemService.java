package com.foodexpress.service;

import com.foodexpress.entity.FoodItem;
import com.foodexpress.entity.Restaurant;
import com.foodexpress.exception.ResourceNotFoundException;
import com.foodexpress.repository.FoodItemRepository;
import com.foodexpress.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodItemService {

    private static final Logger logger =
            LoggerFactory.getLogger(FoodItemService.class);

    private final FoodItemRepository foodItemRepository;
    private final RestaurantRepository restaurantRepository;

    public FoodItem addFoodItem(FoodItem foodItem, Long restaurantId) {

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Restaurant not found with id : " + restaurantId));

        foodItem.setRestaurant(restaurant);

        return foodItemRepository.save(foodItem);
    }

    public List<FoodItem> getAllFoodItems() {
        return foodItemRepository.findAll();
    }

    public FoodItem getFoodItemById(Long foodItemId) {

        return foodItemRepository.findById(foodItemId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Food Item not found with id : " + foodItemId));
    }

    public List<FoodItem> getFoodItemsByCategory(String category) {
        return foodItemRepository.findByCategory(category);
    }

    public List<FoodItem> getFoodItemsByPriceRange(
            BigDecimal minPrice,
            BigDecimal maxPrice) {

        return foodItemRepository.findByPriceBetween(minPrice, maxPrice);
    }

    public FoodItem updateFoodItem(Long foodItemId,
                                   FoodItem updatedFoodItem) {

        FoodItem foodItem = getFoodItemById(foodItemId);

        foodItem.setName(updatedFoodItem.getName());
        foodItem.setDescription(updatedFoodItem.getDescription());
        foodItem.setPrice(updatedFoodItem.getPrice());
        foodItem.setCategory(updatedFoodItem.getCategory());
        foodItem.setAvailable(updatedFoodItem.getAvailable());

        return foodItemRepository.save(foodItem);
    }

    public void deleteFoodItem(Long foodItemId) {

        FoodItem foodItem = getFoodItemById(foodItemId);

        foodItemRepository.delete(foodItem);
    }
}