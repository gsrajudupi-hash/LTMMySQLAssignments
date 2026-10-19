package com.foodexpress.controller;

import com.foodexpress.entity.*;
import com.foodexpress.service.*;
import com.foodexpress.repository.FoodOrderRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService service;
    private final FoodOrderRepository orders;

    public CustomerController(CustomerService service, FoodOrderRepository orders) {
        this.service = service;
        this.orders = orders;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Customer create(@Valid @RequestBody Customer c) {
        return service.create(c);
    }

    @GetMapping
    public List<Customer> all() {
        return service.all();
    }

    @GetMapping("/{id}")
    public Customer get(@PathVariable Long id) {
        return service.get(id);
    }

    @PutMapping("/{id}")
    public Customer update(@PathVariable Long id, @Valid @RequestBody Customer c) {
        return service.update(id, c);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/{id}/orders")
    public List<FoodOrder> history(@PathVariable Long id) {
        service.get(id);
        return orders.findByCustomer_CustomerId(id);
    }
}
