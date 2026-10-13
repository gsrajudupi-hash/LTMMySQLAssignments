package com.foodexpress.service;

import com.foodexpress.entity.Restaurant;
import com.foodexpress.exception.ResourceNotFoundException;
import com.foodexpress.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private static final Logger logger =
            LoggerFactory.getLogger(RestaurantService.class);

    private final RestaurantRepository restaurantRepository;

    public Restaurant addRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public Restaurant getRestaurantById(Long restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Restaurant not found with id : " + restaurantId));
    }

    public Restaurant updateRestaurant(Long restaurantId,
                                       Restaurant updatedRestaurant) {

        Restaurant restaurant = getRestaurantById(restaurantId);

        restaurant.setName(updatedRestaurant.getName());
        restaurant.setLocation(updatedRestaurant.getLocation());
        restaurant.setContactNumber(updatedRestaurant.getContactNumber());
        restaurant.setActive(updatedRestaurant.getActive());

        return restaurantRepository.save(restaurant);
    }

    public void deleteRestaurant(Long restaurantId) {

        Restaurant restaurant = getRestaurantById(restaurantId);

        restaurantRepository.delete(restaurant);
    }
}