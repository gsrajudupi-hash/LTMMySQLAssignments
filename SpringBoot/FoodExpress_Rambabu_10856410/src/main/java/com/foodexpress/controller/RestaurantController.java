package com.foodexpress.controller;

import com.foodexpress.entity.*;
import com.foodexpress.exception.*;
import com.foodexpress.repository.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {
    private final RestaurantRepository repo;
    private final FoodItemRepository foods;

    public RestaurantController(RestaurantRepository r, FoodItemRepository f) {
        repo = r;
        foods = f;
    }

    private Restaurant one(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));
    }

    @PostMapping
    ResponseEntity<Restaurant> add(@RequestBody Restaurant x) {
        return ResponseEntity.status(201).body(repo.save(x));
    }

    @GetMapping
    List<Restaurant> all() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    Restaurant get(@PathVariable Long id) {
        return one(id);
    }

    @PutMapping("/{id}")
    Restaurant update(@PathVariable Long id, @RequestBody Restaurant x) {
        Restaurant r = one(id);
        r.setName(x.getName());
        r.setLocation(x.getLocation());
        r.setContactNumber(x.getContactNumber());
        r.setActive(x.isActive());
        return repo.save(r);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id) {
        repo.delete(one(id));
    }

    @GetMapping("/{id}/menu")
    List<FoodItem> menu(@PathVariable Long id) {
        return foods.findByRestaurantRestaurantIdAndAvailableTrue(id);
    }
}
