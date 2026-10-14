package com.example.Fooddelivery.dto;



import java.util.List;

public record CreateOrderRequest(

        //@NotNull(message = "Customer ID is required")
        Long customerId,

        //@NotNull(message = "Restaurant ID is required")
        Long restaurantId,

        //@NotNull(message = "Address ID is required")
        Long addressId,

       // @NotEmpty(message = "At least one food item is required")
        List< OrderItemRequest> items

) {
}

