package com.foodexpress.service;

import com.foodexpress.dto.*;
import com.foodexpress.entity.*;
import com.foodexpress.exception.*;
import com.foodexpress.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.*;
import java.time.*;
import java.util.*;
/**
 * Service class for managing food orders.
 */
@Service
public class FoodOrderService {
    private final FoodOrderRepository orders;
    private final CustomerRepository customers;
    private final RestaurantRepository restaurants;
    private final AddressRepository addresses;
    private final FoodItemRepository foods;
// Repositories for accessing food orders, customers, restaurants, addresses, and food items.
    public FoodOrderService(FoodOrderRepository o, CustomerRepository c, RestaurantRepository r, AddressRepository a,
            FoodItemRepository f) {
        orders = o;
        customers = c;
        restaurants = r;
        addresses = a;
        foods = f;
    }
// Places a new food order based on the provided request.
    @Transactional
    public FoodOrder place(CreateOrderRequest req) {
        Customer c = customers.findById(req.customerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        Restaurant r = restaurants.findById(req.restaurantId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));
        if (!r.isActive())
            throw new IllegalArgumentException("Restaurant is inactive");
        Address a = addresses.findById(req.addressId())
                .orElseThrow(() -> new ResourceNotFoundException("Address not found"));
        FoodOrder o = new FoodOrder();
        o.setCustomer(c);
        o.setRestaurant(r);
        o.setAddress(a);
        o.setOrderDate(LocalDateTime.now());
        o.setStatus(OrderStatus.PLACED);
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItemRequest x : req.items()) {
            FoodItem f = foods.findById(x.foodItemId())
                    .orElseThrow(() -> new ResourceNotFoundException("Food item not found: " + x.foodItemId()));
            if (!f.isAvailable() || !f.getRestaurant().getRestaurantId().equals(r.getRestaurantId()))
                throw new IllegalArgumentException("Invalid/unavailable food item: " + x.foodItemId());
            OrderItem i = new OrderItem();
            i.setOrder(o);
            i.setFoodItem(f);
            i.setQuantity(x.quantity());
            i.setUnitPrice(f.getPrice());
            i.setSubtotal(f.getPrice().multiply(BigDecimal.valueOf(x.quantity())));
            o.getItems().add(i);
            total = total.add(i.getSubtotal());
        }
        o.setTotalAmount(total);
        return orders.save(o);
    }

    public List<FoodOrder> all() {
        return orders.findAll();
    }

    public FoodOrder one(Long id) {
        return orders.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    public List<FoodOrder> customer(Long id) {
        return orders.findByCustomerCustomerId(id);
    }
// Updates the status of an existing food order.
    @Transactional
    public FoodOrder status(Long id, OrderStatus s) {
        FoodOrder o = one(id);
        if (o.getStatus() == OrderStatus.DELIVERED || o.getStatus() == OrderStatus.CANCELLED)
            throw new IllegalArgumentException("Finalized order cannot be changed");
        o.setStatus(s);
        return o;
    }
// Cancels an order if it is eligible for cancellation.
    @Transactional
    public FoodOrder cancel(Long id) {
        FoodOrder o = one(id);
        if (EnumSet.of(OrderStatus.OUT_FOR_DELIVERY, OrderStatus.DELIVERED, OrderStatus.CANCELLED)
                .contains(o.getStatus()))
            throw new IllegalArgumentException("Order is not eligible for cancellation");
        o.setStatus(OrderStatus.CANCELLED);
        return o;
    }
}
