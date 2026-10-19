package com.foodexpress.service;

import com.foodexpress.entity.*;
import com.foodexpress.exception.ResourceNotFoundException;
import com.foodexpress.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
    private final AddressRepository repo;
    private final CustomerRepository customers;

    public AddressService(AddressRepository repo, CustomerRepository customers) {
        this.repo = repo;
        this.customers = customers;
    }

    public Address create(Long customerId, Address a) {
        Customer c = customers.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + customerId));
        a.setCustomer(c);
        return repo.save(a);
    }

    public List<Address> byCustomer(Long id) {
        return repo.findAll().stream().filter(a -> a.getCustomer().getCustomerId().equals(id)).toList();
    }

    public Address get(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Address not found: " + id));
    }

    public Address update(Long id, Address a) {
        Address x = get(id);
        x.setHouseNumber(a.getHouseNumber());
        x.setStreet(a.getStreet());
        x.setCity(a.getCity());
        x.setPincode(a.getPincode());
        return repo.save(x);
    }

    public void delete(Long id) {
        repo.delete(get(id));
    }
}
