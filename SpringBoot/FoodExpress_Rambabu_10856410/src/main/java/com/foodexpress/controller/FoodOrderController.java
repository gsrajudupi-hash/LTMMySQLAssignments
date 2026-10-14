package com.foodexpress.controller;

import com.foodexpress.dto.*;
import com.foodexpress.entity.*;
import com.foodexpress.service.*;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/orders")
public class FoodOrderController {
    private final FoodOrderService service;

    public FoodOrderController(FoodOrderService s) {
        service = s;
    }

    @PostMapping
    ResponseEntity<FoodOrder> place(@Valid @RequestBody CreateOrderRequest x) {
        return ResponseEntity.status(201).body(service.place(x));
    }

    @GetMapping
    List<FoodOrder> all() {
        return service.all();
    }

    @GetMapping("/{id}")
    FoodOrder one(@PathVariable Long id) {
        return service.one(id);
    }

    @GetMapping("/customer/{id}")
    List<FoodOrder> customer(@PathVariable Long id) {
        return service.customer(id);
    }

    @PatchMapping("/{id}/status")
    FoodOrder status(@PathVariable Long id, @RequestParam OrderStatus status) {
        return service.status(id, status);
    }

    @PatchMapping("/{id}/cancel")
    FoodOrder cancel(@PathVariable Long id) {
        return service.cancel(id);
    }
}
