package com.foodexpress.repository;

import com.foodexpress.entity.FoodOrder;
import com.foodexpress.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodOrderRepository extends JpaRepository<FoodOrder, Long> {

    List<FoodOrder> findByCustomerCustomerId(Long customerId);

    List<FoodOrder> findByRestaurantRestaurantId(Long restaurantId);

    List<FoodOrder> findByCustomerCustomerIdAndStatus(
            Long customerId,
            OrderStatus status
    );
}