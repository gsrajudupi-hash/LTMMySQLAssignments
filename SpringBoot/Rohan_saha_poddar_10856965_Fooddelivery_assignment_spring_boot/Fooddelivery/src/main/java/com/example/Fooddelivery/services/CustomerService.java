package com.example.Fooddelivery.services;

import com.example.Fooddelivery.dto.CustomerRequest;
import com.example.Fooddelivery.dto.CustomerResponse;
import com.example.Fooddelivery.entities.Customer;
import com.example.Fooddelivery.exception.BusinessException;
import com.example.Fooddelivery.exception.ResourceNotFoundException;
import com.example.Fooddelivery.repository.CustomerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public class CustomerService {
    private final CustomerRepository repo;
    private final PasswordEncoder encoder;

    public CustomerService(CustomerRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    public CustomerResponse create(CustomerRequest r){ Customer c=Customer.builder().name(r.name()).email(r.email()).phone(r.phone()).password(encoder.encode(r.password())).build(); return map(repo.save(c)); }
    public List<CustomerResponse> all(){ return repo.findAll().stream().map(this::map).toList(); }
    public CustomerResponse get(Long id){ return map(entity(id)); }
    public CustomerResponse update(Long id, CustomerRequest r){ Customer c=entity(id); c.setName(r.name());c.setEmail(r.email());c.setPhone(r.phone());c.setPassword(encoder.encode(r.password()));return map(repo.save(c)); }
    public void delete(Long id){ Customer c=entity(id); if(!c.getOrders().isEmpty()) throw new BusinessException("Customer with order history cannot be deleted"); repo.delete(c); }
    public Customer entity(Long id){ return repo.findById(id).orElseThrow(()->new ResourceNotFoundException("Customer not found: "+id)); }
    private CustomerResponse map(Customer c){ return new CustomerResponse(c.getCustomerId(),c.getName(),c.getEmail(),c.getPhone()); }
}
