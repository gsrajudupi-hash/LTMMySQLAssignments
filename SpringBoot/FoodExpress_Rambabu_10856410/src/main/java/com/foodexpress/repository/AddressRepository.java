package com.foodexpress.repository;

import com.foodexpress.entity.Address;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//# Repository interface for managing Address entities
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByCustomerCustomerId(Long customerId);
}
