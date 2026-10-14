package com.example.Fooddelivery.services;

import com.example.Fooddelivery.dto.CreateOrderRequest;
import com.example.Fooddelivery.dto.OrderItemRequest;
import com.example.Fooddelivery.dto.OrderItemResponse;
import com.example.Fooddelivery.dto.OrderResponse;
import com.example.Fooddelivery.entities.*;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class FoodOrderService {
    private final FoodOrderRepository repo;
    private final CustomerService customers;
    private final RestaurantService restaurants;
    private final AddressService addresses;
    private final FoodItemService foods;

    public FoodOrderService(FoodOrderRepository repo, CustomerService customers, RestaurantService restaurants, AddressService addresses, FoodItemService foods) {
        this.repo = repo;
        this.customers = customers;
        this.restaurants = restaurants;
        this.addresses = addresses;
        this.foods = foods;
    }

    @Transactional
    public OrderResponse place(CreateOrderRequest r){
        Customer c=customers.entity(r.customerId());
        Restaurant restaurant=restaurants.entity(r.restaurantId());
        Address address=addresses.entity(r.addressId());
        if(address.getCustomer().getCustomerId().longValue()!=c.getCustomerId().longValue()) throw new BusinessException("Address does not belong to customer");
        if(!restaurant.isActive()) throw new BusinessException("Restaurant is inactive");
        FoodOrder order=FoodOrder.builder().customer(c).restaurant(restaurant).deliveryAddress(address).orderDate(LocalDateTime.now()).status(OrderStatus.PLACED).totalAmount(BigDecimal.ZERO).build();
        BigDecimal total=BigDecimal.ZERO;
        for(OrderItemRequest item:r.items()){
            FoodItem food=foods.entity(item.foodItemId());
            if(!food.isAvailable()) throw new BusinessException("Food item unavailable: "+food.getName());
            if(!food.getRestaurant().getRestaurantId().equals(restaurant.getRestaurantId())) throw new BusinessException("All items must belong to selected restaurant");
            BigDecimal subtotal=food.getPrice().multiply(BigDecimal.valueOf(item.quantity()));
            order.getItems().add(OrderItem.builder().order(order).foodItem(food).quantity(item.quantity()).unitPrice(food.getPrice()).subtotal(subtotal).build()); total=total.add(subtotal);
        }
        order.setTotalAmount(total);return map(repo.save(order));
    }
    @Transactional(readOnly=true) public List<OrderResponse> all(){return repo.findAll().stream().map(this::map).toList();}
    @Transactional(readOnly=true) public OrderResponse get(Long id){return map(entity(id));}
    @Transactional(readOnly=true) public List<OrderResponse> byCustomer(Long id){customers.entity(id);return repo.findByCustomerCustomerId(id).stream().map(this::map).toList();}
    @Transactional public OrderResponse updateStatus(Long id,OrderStatus status){FoodOrder o=entity(id);if(o.getStatus()==OrderStatus.CANCELLED||o.getStatus()==OrderStatus.DELIVERED) throw new BusinessException("Final order status cannot be changed");o.setStatus(status);return map(repo.save(o));}
    @Transactional public OrderResponse cancel(Long id){FoodOrder o=entity(id);if(Set.of(OrderStatus.DELIVERED,OrderStatus.OUT_FOR_DELIVERY,OrderStatus.CANCELLED).contains(o.getStatus())) throw new BusinessException("Order cannot be cancelled in status "+o.getStatus());o.setStatus(OrderStatus.CANCELLED);return map(repo.save(o));}
    private FoodOrder entity(Long id){return repo.findById(id).orElseThrow(()->new ResourceNotFoundException("Order not found: "+id));}
    private OrderResponse map(FoodOrder o){List<OrderItemResponse> items=o.getItems().stream().map(i->new OrderItemResponse(i.getFoodItem().getFoodItemId(),i.getFoodItem().getName(),i.getQuantity(),i.getUnitPrice(),i.getSubtotal())).toList();return new OrderResponse(o.getOrderId(),o.getCustomer().getCustomerId(),o.getRestaurant().getRestaurantId(),o.getDeliveryAddress().getAddressId(),o.getOrderDate(),o.getStatus(),o.getTotalAmount(),items);}
}
