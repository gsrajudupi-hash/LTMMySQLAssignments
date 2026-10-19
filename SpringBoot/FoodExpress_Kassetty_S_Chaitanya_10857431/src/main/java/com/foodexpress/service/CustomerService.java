package com.foodexpress.service;

import com.foodexpress.entity.Customer;
import com.foodexpress.exception.ResourceNotFoundException;
import com.foodexpress.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository repo;

    public CustomerService(CustomerRepository repo) {
        this.repo = repo;
    }

    public Customer create(Customer c) {
        return repo.save(c);
    }

    public List<Customer> all() {
        return repo.findAll();
    }

    public Customer get(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + id));
    }

    public Customer update(Long id, Customer c) {
        Customer x = get(id);
        x.setName(c.getName());
        x.setEmail(c.getEmail());
        x.setPhone(c.getPhone());
        if (c.getPassword() != null && !c.getPassword().isBlank()) x.setPassword(c.getPassword());
        return repo.save(x);
    }

    public void delete(Long id) {
        repo.delete(get(id));
    }
}
