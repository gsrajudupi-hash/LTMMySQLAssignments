package com.foodexpress.repository;

import com.foodexpress.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
//# Repository interface for managing Customer entities
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
