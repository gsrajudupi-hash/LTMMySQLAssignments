package com.foodexpress.service;

import com.foodexpress.dto.CreateOrderRequest;
import com.foodexpress.dto.OrderItemRequest;
import com.foodexpress.dto.OrderItemResponse;
import com.foodexpress.dto.OrderResponse;
import com.foodexpress.entity.Address;
import com.foodexpress.entity.Customer;
import com.foodexpress.entity.FoodItem;
import com.foodexpress.entity.FoodOrder;
import com.foodexpress.entity.OrderItem;
import com.foodexpress.entity.OrderStatus;
import com.foodexpress.entity.Restaurant;
import com.foodexpress.exception.ResourceNotFoundException;
import com.foodexpress.repository.AddressRepository;
import com.foodexpress.repository.CustomerRepository;
import com.foodexpress.repository.FoodItemRepository;
import com.foodexpress.repository.FoodOrderRepository;
import com.foodexpress.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodOrderService {

    private static final Logger logger =
            LoggerFactory.getLogger(FoodOrderService.class);

    private final FoodOrderRepository foodOrderRepository;
    private final CustomerRepository customerRepository;
    private final RestaurantRepository restaurantRepository;
    private final AddressRepository addressRepository;
    private final FoodItemRepository foodItemRepository;

    @Transactional
    public OrderResponse placeOrder(CreateOrderRequest request) {

        logger.info(
                "Placing order for customer ID: {} and restaurant ID: {}",
                request.getCustomerId(),
                request.getRestaurantId()
        );

        Customer customer = customerRepository
                .findById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer not found with ID: "
                                + request.getCustomerId()
                ));

        Restaurant restaurant = restaurantRepository
                .findById(request.getRestaurantId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Restaurant not found with ID: "
                                + request.getRestaurantId()
                ));

        if (!Boolean.TRUE.equals(restaurant.getActive())) {
            logger.warn(
                    "Restaurant ID {} is inactive",
                    restaurant.getRestaurantId()
            );

            throw new IllegalStateException(
                    "Restaurant is currently inactive"
            );
        }

        Address address = addressRepository
                .findById(request.getAddressId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Address not found with ID: "
                                + request.getAddressId()
                ));

        if (address.getCustomer() == null ||
                !address.getCustomer()
                        .getCustomerId()
                        .equals(customer.getCustomerId())) {

            throw new IllegalArgumentException(
                    "Delivery address does not belong to the customer"
            );
        }

        FoodOrder foodOrder = new FoodOrder();

        foodOrder.setCustomer(customer);
        foodOrder.setRestaurant(restaurant);
        foodOrder.setDeliveryAddress(address);
        foodOrder.setStatus(OrderStatus.PLACED);
        foodOrder.setTotalAmount(BigDecimal.ZERO);

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderItemRequest itemRequest : request.getItems()) {

            FoodItem foodItem = foodItemRepository
                    .findById(itemRequest.getFoodItemId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Food item not found with ID: "
                                    + itemRequest.getFoodItemId()
                    ));

            if (!Boolean.TRUE.equals(foodItem.getAvailable())) {
                throw new IllegalStateException(
                        foodItem.getName() + " is unavailable"
                );
            }

            if (foodItem.getRestaurant() == null ||
                    !foodItem.getRestaurant()
                            .getRestaurantId()
                            .equals(restaurant.getRestaurantId())) {

                throw new IllegalArgumentException(
                        foodItem.getName()
                                + " does not belong to the selected restaurant"
                );
            }

            if (itemRequest.getQuantity() == null ||
                    itemRequest.getQuantity() <= 0) {

                throw new IllegalArgumentException(
                        "Quantity must be greater than zero"
                );
            }

            BigDecimal unitPrice = foodItem.getPrice();

            BigDecimal subtotal = unitPrice.multiply(
                    BigDecimal.valueOf(itemRequest.getQuantity())
            );

            OrderItem orderItem = new OrderItem();

            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setUnitPrice(unitPrice);
            orderItem.setSubtotal(subtotal);
            orderItem.setFoodItem(foodItem);
            orderItem.setFoodOrder(foodOrder);

            foodOrder.getOrderItems().add(orderItem);

            totalAmount = totalAmount.add(subtotal);
        }

        foodOrder.setTotalAmount(totalAmount);

        FoodOrder savedOrder =
                foodOrderRepository.save(foodOrder);

        logger.info(
                "Order placed successfully. Order ID: {}, total: {}",
                savedOrder.getOrderId(),
                savedOrder.getTotalAmount()
        );

        return convertToResponse(savedOrder);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getAllOrders() {

        logger.info("Fetching all orders");

        return foodOrderRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public OrderResponse getOrderById(Long orderId) {

        logger.info("Fetching order with ID: {}", orderId);

        FoodOrder foodOrder = findOrderById(orderId);

        return convertToResponse(foodOrder);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getOrdersByCustomer(
            Long customerId) {

        logger.info(
                "Fetching orders for customer ID: {}",
                customerId
        );

        if (!customerRepository.existsById(customerId)) {
            throw new ResourceNotFoundException(
                    "Customer not found with ID: " + customerId
            );
        }

        return foodOrderRepository
                .findByCustomerCustomerId(customerId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    @Transactional
    public OrderResponse updateOrderStatus(
            Long orderId,
            OrderStatus newStatus) {

        logger.info(
                "Updating order ID {} to status {}",
                orderId,
                newStatus
        );

        FoodOrder foodOrder = findOrderById(orderId);

        if (foodOrder.getStatus() == OrderStatus.CANCELLED) {
            throw new IllegalStateException(
                    "Cancelled order status cannot be updated"
            );
        }

        if (foodOrder.getStatus() == OrderStatus.DELIVERED) {
            throw new IllegalStateException(
                    "Delivered order status cannot be updated"
            );
        }

        foodOrder.setStatus(newStatus);

        FoodOrder updatedOrder =
                foodOrderRepository.save(foodOrder);

        return convertToResponse(updatedOrder);
    }

    @Transactional
    public OrderResponse cancelOrder(Long orderId) {

        logger.info("Cancelling order ID: {}", orderId);

        FoodOrder foodOrder = findOrderById(orderId);

        if (foodOrder.getStatus() == OrderStatus.DELIVERED) {
            throw new IllegalStateException(
                    "Delivered order cannot be cancelled"
            );
        }

        if (foodOrder.getStatus() ==
                OrderStatus.OUT_FOR_DELIVERY) {

            throw new IllegalStateException(
                    "Order cannot be cancelled after it is out for delivery"
            );
        }

        if (foodOrder.getStatus() == OrderStatus.CANCELLED) {
            throw new IllegalStateException(
                    "Order is already cancelled"
            );
        }

        foodOrder.setStatus(OrderStatus.CANCELLED);

        FoodOrder cancelledOrder =
                foodOrderRepository.save(foodOrder);

        logger.info(
                "Order ID {} cancelled successfully",
                orderId
        );

        return convertToResponse(cancelledOrder);
    }

    private FoodOrder findOrderById(Long orderId) {

        return foodOrderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Order not found with ID: " + orderId
                ));
    }

    private OrderResponse convertToResponse(
            FoodOrder foodOrder) {

        List<OrderItemResponse> itemResponses =
                foodOrder.getOrderItems()
                        .stream()
                        .map(orderItem ->
                                new OrderItemResponse(
                                        orderItem.getFoodItem()
                                                .getFoodItemId(),
                                        orderItem.getFoodItem()
                                                .getName(),
                                        orderItem.getQuantity(),
                                        orderItem.getUnitPrice(),
                                        orderItem.getSubtotal()
                                ))
                        .toList();

        Address address = foodOrder.getDeliveryAddress();

        String completeAddress =
                address.getHouseNumber()
                        + ", "
                        + address.getStreet()
                        + ", "
                        + address.getCity()
                        + " - "
                        + address.getPincode();

        return new OrderResponse(
                foodOrder.getOrderId(),
                foodOrder.getOrderDate(),
                foodOrder.getStatus(),
                foodOrder.getTotalAmount(),
                foodOrder.getCustomer().getCustomerId(),
                foodOrder.getCustomer().getName(),
                foodOrder.getRestaurant().getRestaurantId(),
                foodOrder.getRestaurant().getName(),
                address.getAddressId(),
                completeAddress,
                itemResponses
        );
    }
}