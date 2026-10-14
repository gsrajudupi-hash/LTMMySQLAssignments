package com.example.Fooddelivery.services;

import com.example.Fooddelivery.dto.AddressRequest;
import com.example.Fooddelivery.dto.AddressResponse;
import com.example.Fooddelivery.entities.Address;
import com.example.Fooddelivery.entities.Customer;
import com.example.Fooddelivery.exception.ResourceNotFoundException;
import com.example.Fooddelivery.repository.AddressRepository;

import java.util.List;

public class AddressService {
    private final AddressRepository repo;
    private final CustomerService customers;

    public AddressService(AddressRepository repo, CustomerService customers) {
        this.repo = repo;
        this.customers = customers;
    }

    public AddressResponse create(Long customerId, AddressRequest r) {
        Customer c = customers.entity(customerId);
        return map(repo.save(Address.builder().houseNumber(r.houseNumber()).street(r.street()).city(r.city()).pincode(r.pincode()).customer(c).build()));
    }

    public List<AddressResponse> byCustomer(Long id) {
        customers.entity(id);
        return repo.findByCustomerCustomerId(id).stream().map(this::map).toList();
    }

    public AddressResponse get(Long id) {
        return map(entity(id));
    }

    public AddressResponse update(Long id, AddressRequest r) {
        Address a = entity(id);
        a.setHouseNumber(r.houseNumber());
        a.setStreet(r.street());
        a.setCity(r.city());
        a.setPincode(r.pincode());
        return map(repo.save(a));
    }

    public void delete(Long id) {
        repo.delete(entity(id));
    }

    public Address entity(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Address not found: " + id));
    }

    private AddressResponse map(Address a) {
        return new AddressResponse(a.getAddressId(), a.getHouseNumber(), a.getStreet(), a.getCity(), a.getPincode(), a.getCustomer().getCustomerId());
    }
}

