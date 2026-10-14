package com.example.Fooddelivery.repository;
//import com.foodexpress.entity.Address;
import com.example.Fooddelivery.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AddressRepository extends JpaRepository<Address,Long>{ List<Address> findByCustomerCustomerId(Long customerId); }


