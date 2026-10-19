package com.foodexpress.repository;
import com.foodexpress.entity.FoodOrder;
import org.springframework.data.jpa.repository.JpaRepository;

 import java.util.List;

public interface FoodOrderRepository extends JpaRepository<FoodOrder, Long> {

 List<FoodOrder> findByCustomer_CustomerId(Long customerId);

}
