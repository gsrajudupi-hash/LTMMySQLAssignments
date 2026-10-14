package com.example.Fooddelivery.controller;

//import com.foodexpress.dto.*;
//import com.foodexpress.service.AddressService;
//import jakarta.validation.Valid;
import com.example.Fooddelivery.dto.AddressRequest;
import com.example.Fooddelivery.dto.AddressResponse;
import com.example.Fooddelivery.services.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/addresses") @RequiredArgsConstructor
public class AddressController {
    private final AddressService service;

    @PostMapping("/customer/{customerId}")
    public ResponseEntity<AddressResponse> create(@PathVariable Long customerId, @RequestBody AddressRequest r) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(customerId, r));
    }

    @GetMapping("/customer/{customerId}")
    public List<AddressResponse> byCustomer(@PathVariable Long customerId) {
        return service.byCustomer(customerId);
    }

    @GetMapping("/{id}")
    public AddressResponse get(@PathVariable Long id) {
        return service.get(id);
    }

    @PutMapping("/{id}")
    public AddressResponse update(@PathVariable Long id, @RequestBody AddressRequest r) {
        return service.update(id, r);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}


