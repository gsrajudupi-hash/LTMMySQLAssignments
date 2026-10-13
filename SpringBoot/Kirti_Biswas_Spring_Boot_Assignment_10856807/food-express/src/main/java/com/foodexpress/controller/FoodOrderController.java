package com.foodexpress.controller;

import com.foodexpress.dto.CreateOrderRequest;
import com.foodexpress.dto.OrderResponse;
import com.foodexpress.entity.OrderStatus;
import com.foodexpress.service.FoodOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class FoodOrderController {

    private final FoodOrderService foodOrderService;

    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(
            @Valid @RequestBody CreateOrderRequest request) {

        OrderResponse response =
                foodOrderService.placeOrder(request);

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {

        return ResponseEntity.ok(
                foodOrderService.getAllOrders()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                foodOrderService.getOrderById(id)
        );
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderResponse>>
    getOrdersByCustomer(
            @PathVariable Long customerId) {

        return ResponseEntity.ok(
                foodOrderService
                        .getOrdersByCustomer(customerId)
        );
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus status) {

        return ResponseEntity.ok(
                foodOrderService.updateOrderStatus(
                        id,
                        status
                )
        );
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<OrderResponse> cancelOrder(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                foodOrderService.cancelOrder(id)
        );
    }
}