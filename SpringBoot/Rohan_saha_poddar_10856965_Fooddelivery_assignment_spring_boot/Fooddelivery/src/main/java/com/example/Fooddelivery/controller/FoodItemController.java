package com.example.Fooddelivery.controller;

import com.example.Fooddelivery.dto.FoodItemRequest;
import com.example.Fooddelivery.dto.FoodItemResponse;
import com.example.Fooddelivery.services.FoodItemService;

//import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food-items")
@RequiredArgsConstructor
public class FoodItemController {

    private final FoodItemService service;

    @PostMapping
    public ResponseEntity<FoodItemResponse> create(
             @RequestBody FoodItemRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(request));
    }

    @GetMapping
    public Page<FoodItemResponse> all(
            @PageableDefault(size = 10, sort = "foodItemId")
            Pageable pageable) {

        return service.all(pageable);
    }

    @GetMapping("/{id}")
    public FoodItemResponse get(@PathVariable Long id) {
        return service.get(id);
    }

    @PutMapping("/{id}")
    public FoodItemResponse update(
            @PathVariable Long id,
             @RequestBody FoodItemRequest request) {

        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        service.delete(id);

        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping("/category/{category}")
    public List<FoodItemResponse> category(
            @PathVariable String category) {

        return service.category(category);
    }
}