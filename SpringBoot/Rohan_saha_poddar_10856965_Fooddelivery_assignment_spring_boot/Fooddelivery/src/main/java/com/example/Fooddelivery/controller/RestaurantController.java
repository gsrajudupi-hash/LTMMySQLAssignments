package com.example.Fooddelivery.controller;

import com.example.Fooddelivery.dto.FoodItemResponse;
import com.example.Fooddelivery.dto.RestaurantRequest;
import com.example.Fooddelivery.dto.RestaurantResponse;
import com.example.Fooddelivery.services.FoodItemService;
import com.example.Fooddelivery.services.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//import com.foodexpress.dto.*;import com.foodexpress.service.*;import jakarta.validation.Valid;import lombok.RequiredArgsConstructor;import org.springframework.http.*;import org.springframework.web.bind.annotation.*;import java.util.List;
@RestController
@RequestMapping("/api/restaurants") @RequiredArgsConstructor
public class RestaurantController{
    private final RestaurantService service;private final FoodItemService foods;
    @PostMapping
    public ResponseEntity<RestaurantResponse> create(@RequestBody RestaurantRequest r){return ResponseEntity.status(HttpStatus.CREATED).body(service.create(r));}
    @GetMapping
    public List<RestaurantResponse> all(){return service.all();}@GetMapping("/{id}") public RestaurantResponse get(@PathVariable Long id){return service.get(id);}
    @PutMapping("/{id}") public RestaurantResponse update(@PathVariable Long id, @RequestBody RestaurantRequest r){return service.update(id,r);}
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id){service.delete(id);return ResponseEntity.noContent().build();}
    @GetMapping("/{id}/menu") public List<FoodItemResponse> menu(@PathVariable Long id){return foods.menu(id);}
}
