package com.example.Fooddelivery.controller;

//import com.foodexpress.dto.*;
//import com.foodexpress.service.FoodOrderService;
//import jakarta.validation.Valid;
import com.example.Fooddelivery.dto.CreateOrderRequest;
import com.example.Fooddelivery.dto.OrderResponse;
import com.example.Fooddelivery.dto.OrderStatusRequest;
import com.example.Fooddelivery.services.FoodOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/orders") @RequiredArgsConstructor public class FoodOrderController{
    private final FoodOrderService service;
    @PostMapping public ResponseEntity<OrderResponse> place(@RequestBody CreateOrderRequest r){return ResponseEntity.status(HttpStatus.CREATED).body(service.place(r));}
    @GetMapping public List<OrderResponse> all(){return service.all();}@GetMapping("/{id}") public OrderResponse get(@PathVariable Long id){return service.get(id);}
    @GetMapping("/customer/{customerId}") public List<OrderResponse> byCustomer(@PathVariable Long customerId){return service.byCustomer(customerId);}
    @PatchMapping("/{id}/status") public OrderResponse status(@PathVariable Long id, @RequestBody OrderStatusRequest r){return service.updateStatus(id,r.status());}
    @PatchMapping("/{id}/cancel") public OrderResponse cancel(@PathVariable Long id){return service.cancel(id);}
}
