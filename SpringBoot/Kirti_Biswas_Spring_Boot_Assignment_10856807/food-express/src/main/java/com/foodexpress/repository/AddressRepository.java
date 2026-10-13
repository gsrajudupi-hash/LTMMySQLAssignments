package com.foodexpress.repository;

import com.foodexpress.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByCustomerCustomerId(Long customerId);
}