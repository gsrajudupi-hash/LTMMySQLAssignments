package com.example.Fooddelivery.controller;

//import com.foodexpress.dto.*;
//import com.foodexpress.service.*;
//mport jakarta.validation.Valid;
import com.example.Fooddelivery.dto.CustomerRequest;
import com.example.Fooddelivery.dto.CustomerResponse;
import com.example.Fooddelivery.dto.OrderResponse;
import com.example.Fooddelivery.services.CustomerService;
import com.example.Fooddelivery.services.FoodOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/customers") @RequiredArgsConstructor public class CustomerController{
    private final CustomerService service;private final FoodOrderService orders;
    @PostMapping public ResponseEntity<CustomerResponse> create( @RequestBody CustomerRequest r){return ResponseEntity.status(HttpStatus.CREATED).body(service.create(r));}
    @GetMapping public List<CustomerResponse> all(){return service.all();}
    @GetMapping("/{id}") public CustomerResponse get(@PathVariable Long id){return service.get(id);}
    @PutMapping("/{id}") public CustomerResponse update(@PathVariable Long id, @RequestBody CustomerRequest r){return service.update(id,r);}
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id){service.delete(id);return ResponseEntity.noContent().build();}
    @GetMapping("/{id}/orders") public List<OrderResponse> orders(@PathVariable Long id){return orders.byCustomer(id);}
}
