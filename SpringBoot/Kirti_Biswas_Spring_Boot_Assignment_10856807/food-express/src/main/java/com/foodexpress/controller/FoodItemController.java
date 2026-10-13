package com.foodexpress.controller;

import com.foodexpress.entity.FoodItem;
import com.foodexpress.service.FoodItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/food-items")
@RequiredArgsConstructor
public class FoodItemController {

    private final FoodItemService foodItemService;

    @PostMapping
    public FoodItem addFoodItem(
            @RequestParam Long restaurantId,
            @Valid @RequestBody FoodItem foodItem) {

        return foodItemService.addFoodItem(foodItem, restaurantId);
    }

    @GetMapping
    public List<FoodItem> getAllFoodItems() {
        return foodItemService.getAllFoodItems();
    }

    @GetMapping("/{id}")
    public FoodItem getFoodItemById(
            @PathVariable Long id) {

        return foodItemService.getFoodItemById(id);
    }

    @PutMapping("/{id}")
    public FoodItem updateFoodItem(
            @PathVariable Long id,
            @Valid @RequestBody FoodItem foodItem) {

        return foodItemService.updateFoodItem(id, foodItem);
    }

    @DeleteMapping("/{id}")
    public String deleteFoodItem(
            @PathVariable Long id) {

        foodItemService.deleteFoodItem(id);
        return "Food item deleted successfully";
    }

    @GetMapping("/category/{category}")
    public List<FoodItem> getFoodItemsByCategory(
            @PathVariable String category) {

        return foodItemService.getFoodItemsByCategory(category);
    }

    @GetMapping("/price-range")
    public List<FoodItem> getFoodItemsByPriceRange(
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice) {

        return foodItemService.getFoodItemsByPriceRange(
                minPrice,
                maxPrice
        );
    }
}