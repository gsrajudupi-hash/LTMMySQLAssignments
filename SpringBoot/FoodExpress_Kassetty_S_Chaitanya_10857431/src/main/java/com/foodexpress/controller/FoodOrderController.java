package com.foodexpress.controller;

import com.foodexpress.dto.CreateOrderRequest;
import com.foodexpress.entity.*;
import com.foodexpress.service.FoodOrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class FoodOrderController {
    public record StatusRequest(OrderStatus status) {
    }

    private final FoodOrderService service;

    public FoodOrderController(FoodOrderService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FoodOrder place(@Valid @RequestBody CreateOrderRequest request) {
        return service.place(request);
    }

    @GetMapping
    public List<FoodOrder> all() {
        return service.all();
    }

    @GetMapping("/{id}")
    public FoodOrder get(@PathVariable Long id) {
        return service.get(id);
    }

    @GetMapping("/customer/{customerId}")
    public List<FoodOrder> byCustomer(@PathVariable Long customerId) {
        return service.byCustomer(customerId);
    }

    @PatchMapping("/{id}/status")
    public FoodOrder status(@PathVariable Long id, @RequestBody StatusRequest request) {
        if (request.status() == null) throw new IllegalArgumentException("status is required");
        return service.status(id, request.status());
    }

    @PatchMapping("/{id}/cancel")
    public FoodOrder cancel(@PathVariable Long id) {
        return service.cancel(id);
    }
}
