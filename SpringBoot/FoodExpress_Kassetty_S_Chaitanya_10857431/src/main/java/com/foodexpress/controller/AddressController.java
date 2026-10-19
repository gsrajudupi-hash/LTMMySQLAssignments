package com.foodexpress.controller;

import com.foodexpress.entity.Address;
import com.foodexpress.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {
    private final AddressService service;

    public AddressController(AddressService service) {
        this.service = service;
    }

    @PostMapping("/customer/{customerId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Address create(@PathVariable Long customerId, @Valid @RequestBody Address a) {
        return service.create(customerId, a);
    }

    @GetMapping("/customer/{customerId}")
    public List<Address> byCustomer(@PathVariable Long customerId) {
        return service.byCustomer(customerId);
    }

    @GetMapping("/{id}")
    public Address get(@PathVariable Long id) {
        return service.get(id);
    }

    @PutMapping("/{id}")
    public Address update(@PathVariable Long id, @Valid @RequestBody Address a) {
        return service.update(id, a);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
