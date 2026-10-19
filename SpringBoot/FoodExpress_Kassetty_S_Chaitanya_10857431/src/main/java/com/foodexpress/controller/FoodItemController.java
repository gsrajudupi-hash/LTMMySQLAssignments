package com.foodexpress.controller;

import com.foodexpress.entity.*;
import com.foodexpress.service.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/food-items")
public class FoodItemController {
    private final FoodItemService service;

    public FoodItemController(FoodItemService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FoodItem create(@Valid @RequestBody FoodItemRequest req) {
        return service.create(req.item(), req.restaurantId());
    }

    @GetMapping
    public List<FoodItem> all() {
        return service.all();
    }

    @GetMapping("/{id}")
    public FoodItem get(@PathVariable Long id) {
        return service.get(id);
    }

    @PutMapping("/{id}")
    public FoodItem update(@PathVariable Long id, @Valid @RequestBody FoodItem f) {
        return service.update(id, f);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/category/{category}")
    public List<FoodItem> category(@PathVariable String category) {
        return service.category(category);
    }

    public record FoodItemRequest(@Valid FoodItem item, Long restaurantId) {
    }
}
