package com.foodexpress.controller;

import com.foodexpress.entity.Address;
import com.foodexpress.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/customer/{customerId}")
    public Address addAddress(
            @PathVariable Long customerId,
            @Valid @RequestBody Address address) {

        return addressService.addAddress(customerId, address);
    }

    @GetMapping("/customer/{customerId}")
    public List<Address> getCustomerAddresses(
            @PathVariable Long customerId) {

        return addressService.getAddressesByCustomer(customerId);
    }

    @GetMapping("/{id}")
    public Address getAddressById(
            @PathVariable Long id) {

        return addressService.getAddressById(id);
    }

    @PutMapping("/{id}")
    public Address updateAddress(
            @PathVariable Long id,
            @Valid @RequestBody Address address) {

        return addressService.updateAddress(id, address);
    }

    @DeleteMapping("/{id}")
    public String deleteAddress(
            @PathVariable Long id) {

        addressService.deleteAddress(id);

        return "Address deleted successfully";
    }
}