package com.foodexpress.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.*;
/**
 * Entity class representing a Restaurant.
 */
@Entity
@Table(name = "restaurants")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restaurantId;
    @Column(nullable = false)
    private String name;
    private String location;
    private String contactNumber;
    private boolean active = true;
    @OneToMany(mappedBy = "restaurant")
    @JsonIgnore
    private List<FoodItem> foodItems = new ArrayList<>();

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long v) {
        restaurantId = v;
    }

    public String getName() {
        return name;
    }

    public void setName(String v) {
        name = v;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String v) {
        location = v;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String v) {
        contactNumber = v;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean v) {
        active = v;
    }
}
