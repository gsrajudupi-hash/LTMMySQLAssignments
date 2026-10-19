package com.foodexpress.controller;

import com.foodexpress.entity.*;
import com.foodexpress.service.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {
    private final RestaurantService service;
    private final FoodItemService foods;

    public RestaurantController(RestaurantService service, FoodItemService foods) {
        this.service = service;
        this.foods = foods;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurant create(@Valid @RequestBody Restaurant r) {
        return service.create(r);
    }

    @GetMapping
    public List<Restaurant> all() {
        return service.all();
    }

    @GetMapping("/{id}")
    public Restaurant get(@PathVariable Long id) {
        return service.get(id);
    }

    @PutMapping("/{id}")
    public Restaurant update(@PathVariable Long id, @Valid @RequestBody Restaurant r) {
        return service.update(id, r);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/{id}/menu")
    public List<FoodItem> menu(@PathVariable Long id) {
        service.get(id);
        return foods.menu(id);
    }
}
