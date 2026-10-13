package com.foodexpress.controller;

import com.foodexpress.entity.FoodItem;
import com.foodexpress.entity.Restaurant;
import com.foodexpress.service.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping
    public Restaurant addRestaurant(
            @Valid @RequestBody Restaurant restaurant) {

        return restaurantService.addRestaurant(restaurant);
    }

    @GetMapping
    public List<Restaurant> getAllRestaurants() {

        return restaurantService.getAllRestaurants();
    }

    @GetMapping("/{id}")
    public Restaurant getRestaurantById(
            @PathVariable Long id) {

        return restaurantService.getRestaurantById(id);
    }

    @PutMapping("/{id}")
    public Restaurant updateRestaurant(
            @PathVariable Long id,
            @Valid @RequestBody Restaurant restaurant) {

        return restaurantService.updateRestaurant(id, restaurant);
    }

    @DeleteMapping("/{id}")
    public String deleteRestaurant(
            @PathVariable Long id) {

        restaurantService.deleteRestaurant(id);

        return "Restaurant deleted successfully";
    }

    @GetMapping("/{id}/menu")
    public List<FoodItem> getRestaurantMenu(
            @PathVariable Long id) {

        return restaurantService.getRestaurantById(id)
                .getFoodItems();
    }
}