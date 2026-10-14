package com.foodexpress.repository;

import com.foodexpress.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import java.util.*;
//# Repository interface for managing FoodOrder entities
public interface FoodOrderRepository extends JpaRepository<FoodOrder, Long> {
    //# Custom implementation for findAll with entity graph to fetch related entities eagerly
    @Override
    @EntityGraph(attributePaths = { "items", "items.foodItem", "customer", "restaurant", "address" })
    List<FoodOrder> findAll();
//# Custom implementation for findAll with entity graph to fetch related entities eagerly

    @Override
    @EntityGraph(attributePaths = { "items", "items.foodItem", "customer", "restaurant", "address" })
    Optional<FoodOrder> findById(Long id);

    //# Custom implementation for findByCustomerCustomerId with entity graph to fetch related entities eagerly
    @EntityGraph(attributePaths = { "items", "items.foodItem", "customer", "restaurant", "address" })
    List<FoodOrder> findByCustomerCustomerId(Long customerId);
    //# Custom implementation for findByCustomerCustomerIdAndStatus with entity graph to fetch related entities eagerly
    @EntityGraph(attributePaths = { "items", "items.foodItem", "customer", "restaurant", "address" })
    List<FoodOrder> findByCustomerCustomerIdAndStatus(Long customerId, OrderStatus status);
}
