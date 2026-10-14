package com.foodexpress.controller;

import com.foodexpress.entity.*;
import com.foodexpress.exception.*;
import com.foodexpress.repository.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {
    private final AddressRepository repo;
    private final CustomerRepository customers;

    public AddressController(AddressRepository r, CustomerRepository c) {
        repo = r;
        customers = c;
    }

    private Address one(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Address not found"));
    }

    @PostMapping("/customer/{id}")
    ResponseEntity<Address> add(@PathVariable Long id, @RequestBody Address a) {
        a.setCustomer(customers.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found")));
        return ResponseEntity.status(201).body(repo.save(a));
    }

    @GetMapping("/customer/{id}")
    List<Address> byCustomer(@PathVariable Long id) {
        return repo.findByCustomerCustomerId(id);
    }

    @GetMapping("/{id}")
    Address get(@PathVariable Long id) {
        return one(id);
    }

    @PutMapping("/{id}")
    Address update(@PathVariable Long id, @RequestBody Address x) {
        Address a = one(id);
        a.setHouseNumber(x.getHouseNumber());
        a.setStreet(x.getStreet());
        a.setCity(x.getCity());
        a.setPincode(x.getPincode());
        return repo.save(a);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id) {
        repo.delete(one(id));
    }
}
