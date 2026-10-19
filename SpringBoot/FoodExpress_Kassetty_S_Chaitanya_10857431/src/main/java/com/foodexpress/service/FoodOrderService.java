package com.foodexpress.service;

import com.foodexpress.dto.*;
import com.foodexpress.entity.*;
import com.foodexpress.exception.ResourceNotFoundException;
import com.foodexpress.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class FoodOrderService {
    private final FoodOrderRepository orders;
    private final CustomerRepository customers;
    private final RestaurantRepository restaurants;
    private final AddressRepository addresses;
    private final FoodItemRepository foods;

    public FoodOrderService(FoodOrderRepository orders, CustomerRepository customers, RestaurantRepository restaurants, AddressRepository addresses, FoodItemRepository foods) {
        this.orders = orders;
        this.customers = customers;
        this.restaurants = restaurants;
        this.addresses = addresses;
        this.foods = foods;
    }

    @Transactional
    public FoodOrder place(CreateOrderRequest req) {
        Customer c = customers.findById(req.customerId()).orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        Restaurant r = restaurants.findById(req.restaurantId()).orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));
        Address a = addresses.findById(req.addressId()).orElseThrow(() -> new ResourceNotFoundException("Address not found"));
        if (!r.isActive()) throw new IllegalArgumentException("Restaurant is inactive");
        if (!a.getCustomer().getCustomerId().equals(c.getCustomerId()))
            throw new IllegalArgumentException("Address does not belong to customer");
        FoodOrder order = new FoodOrder();
        order.setCustomer(c);
        order.setRestaurant(r);
        order.setAddress(a);
        order.setStatus(OrderStatus.PLACED);
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItemRequest itemReq : req.items()) {
            if (itemReq.quantity() < 1) throw new IllegalArgumentException("Quantity must be positive");
            FoodItem food = foods.findById(itemReq.foodItemId()).orElseThrow(() -> new ResourceNotFoundException("Food item not found: " + itemReq.foodItemId()));
            if (!food.isAvailable()) throw new IllegalArgumentException("Food item is unavailable: " + food.getName());
            if (!food.getRestaurant().getRestaurantId().equals(r.getRestaurantId()))
                throw new IllegalArgumentException("Food item belongs to another restaurant");
            OrderItem item = new OrderItem();
            item.setFoodOrder(order);
            item.setFoodItem(food);
            item.setQuantity(itemReq.quantity());
            item.setUnitPrice(food.getPrice());
            item.setSubtotal(food.getPrice().multiply(BigDecimal.valueOf(itemReq.quantity())));
            order.getItems().add(item);
            total = total.add(item.getSubtotal());
        }
        order.setTotalAmount(total);
        return orders.save(order);
    }

    public List<FoodOrder> all() {
        return orders.findAll();
    }

    public FoodOrder get(Long id) {
        return orders.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found: " + id));
    }

    public List<FoodOrder> byCustomer(Long id) {
        return orders.findByCustomer_CustomerId(id);
    }

    @Transactional
    public FoodOrder status(Long id, OrderStatus status) {
        FoodOrder o = get(id);
        if (o.getStatus() == OrderStatus.CANCELLED)
            throw new IllegalArgumentException("Cancelled order cannot be updated");
        o.setStatus(status);
        return orders.save(o);
    }

    @Transactional
    public FoodOrder cancel(Long id) {
        FoodOrder o = get(id);
        if (o.getStatus() == OrderStatus.DELIVERED)
            throw new IllegalArgumentException("Delivered orders cannot be cancelled");
        if (o.getStatus() == OrderStatus.CANCELLED) throw new IllegalArgumentException("Order is already cancelled");
        o.setStatus(OrderStatus.CANCELLED);
        return orders.save(o);
    }
}
