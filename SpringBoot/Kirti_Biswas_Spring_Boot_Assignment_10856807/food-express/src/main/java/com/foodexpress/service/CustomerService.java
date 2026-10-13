package com.foodexpress.service;

import com.foodexpress.entity.Customer;
import com.foodexpress.exception.ResourceNotFoundException;
import com.foodexpress.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private static final Logger logger =
            LoggerFactory.getLogger(CustomerService.class);


    private final CustomerRepository customerRepository;

    public Customer registerCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Customer not found with id : " + customerId));
    }

    public Customer updateCustomer(Long customerId,
                                   Customer updatedCustomer) {

        Customer customer = getCustomerById(customerId);

        customer.setName(updatedCustomer.getName());
        customer.setEmail(updatedCustomer.getEmail());
        customer.setPhone(updatedCustomer.getPhone());
        customer.setPassword(updatedCustomer.getPassword());

        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long customerId) {

        Customer customer = getCustomerById(customerId);

        customerRepository.delete(customer);
    }
}