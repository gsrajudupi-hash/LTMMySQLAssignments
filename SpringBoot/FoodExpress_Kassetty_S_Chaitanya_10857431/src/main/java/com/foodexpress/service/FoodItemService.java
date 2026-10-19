package com.foodexpress.service;

import com.foodexpress.entity.*;
import com.foodexpress.exception.ResourceNotFoundException;
import com.foodexpress.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodItemService {
    private final FoodItemRepository repo;
    private final RestaurantRepository restaurants;

    public FoodItemService(FoodItemRepository repo, RestaurantRepository restaurants) {
        this.repo = repo;
        this.restaurants = restaurants;
    }

    public FoodItem create(FoodItem x, Long restaurantId) {
        Restaurant r = restaurants.findById(restaurantId).orElseThrow(() -> new ResourceNotFoundException("Restaurant not found: " + restaurantId));
        x.setRestaurant(r);
        return repo.save(x);
    }

    public List<FoodItem> all() {
        return repo.findAll();
    }

    public FoodItem get(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Food item not found: " + id));
    }

    public FoodItem update(Long id, FoodItem f) {
        FoodItem x = get(id);
        x.setName(f.getName());
        x.setDescription(f.getDescription());
        x.setPrice(f.getPrice());
        x.setCategory(f.getCategory());
        x.setAvailable(f.isAvailable());
        return repo.save(x);
    }

    public void delete(Long id) {
        repo.delete(get(id));
    }

    public List<FoodItem> category(String category) {
        return repo.findByCategoryIgnoreCase(category);
    }

    public List<FoodItem> menu(Long restaurantId) {
        return repo.findByRestaurant_RestaurantId(restaurantId);
    }
}
