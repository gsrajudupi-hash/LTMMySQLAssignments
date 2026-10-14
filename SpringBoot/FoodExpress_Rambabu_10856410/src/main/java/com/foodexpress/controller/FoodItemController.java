package com.foodexpress.controller;

import com.foodexpress.dto.*;
import com.foodexpress.entity.*;
import com.foodexpress.exception.*;
import com.foodexpress.repository.*;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/food-items")
public class FoodItemController {
    private final FoodItemRepository repo;
    private final RestaurantRepository restaurants;

    public FoodItemController(FoodItemRepository r, RestaurantRepository x) {
        repo = r;
        restaurants = x;
    }

    private FoodItem one(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Food item not found"));
    }

    private void map(FoodItem f, FoodItemRequest x) {
        f.setName(x.name());
        f.setDescription(x.description());
        f.setPrice(x.price());
        f.setCategory(x.category());
        f.setAvailable(x.available());
        f.setRestaurant(restaurants.findById(x.restaurantId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found")));
    }

    @PostMapping
    ResponseEntity<FoodItem> add(@Valid @RequestBody FoodItemRequest x) {
        FoodItem f = new FoodItem();
        map(f, x);
        return ResponseEntity.status(201).body(repo.save(f));
    }

    @GetMapping
    List<FoodItem> all() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    FoodItem get(@PathVariable Long id) {
        return one(id);
    }

    @PutMapping("/{id}")
    FoodItem update(@PathVariable Long id, @Valid @RequestBody FoodItemRequest x) {
        FoodItem f = one(id);
        map(f, x);
        return repo.save(f);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id) {
        repo.delete(one(id));
    }

    @GetMapping("/category/{category}")
    List<FoodItem> category(@PathVariable String category) {
        return repo.findByCategoryIgnoreCase(category);
    }
}
