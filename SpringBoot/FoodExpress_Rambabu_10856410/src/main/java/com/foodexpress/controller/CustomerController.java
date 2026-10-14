package com.foodexpress.controller;

import com.foodexpress.entity.*;
import com.foodexpress.exception.*;
import com.foodexpress.repository.*;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerRepository repo;
    private final FoodOrderRepository orders;
    private final AddressRepository addressRepo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public CustomerController(CustomerRepository r, FoodOrderRepository o, AddressRepository a) {
        repo = r;
        orders = o;
        addressRepo = a;
    }

    @PostMapping
    ResponseEntity<Customer> add(@RequestBody Customer c) {
        c.setPassword(encoder.encode(c.getPassword()));
        return ResponseEntity.status(201).body(repo.save(c));
    }

    @GetMapping
    List<Customer> all() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    Customer one(@PathVariable Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
    }
@GetMapping("/customer/{customerId}")
public List<Address> getAddressesByCustomer(@PathVariable Long customerId) {
    return addressRepo.findByCustomerCustomerId(customerId);
}
    @PutMapping("/{id}")
    Customer update(@PathVariable Long id, @RequestBody Customer x) {
        Customer c = one(id);
        c.setName(x.getName());
        c.setEmail(x.getEmail());
        c.setPhone(x.getPhone());
        return repo.save(c);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id) {
        repo.delete(one(id));
    }

    @GetMapping("/{id}/orders")
    List<FoodOrder> history(@PathVariable Long id) {
        return orders.findByCustomerCustomerId(id);
    }
}
