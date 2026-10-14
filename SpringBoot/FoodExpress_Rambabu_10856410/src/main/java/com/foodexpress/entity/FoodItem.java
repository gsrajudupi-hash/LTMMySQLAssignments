package com.foodexpress.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;
/**
 * Entity class representing a FoodItem.
 */
@Entity
@Table(name = "food_items")
public class FoodItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long foodItemId;
    @Column(nullable = false)
    private String name;
    private String description;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    private String category;
    private boolean available = true;
    //# Many-to-one relationship with Restaurant entity, fetched lazily and ignored in JSON serialization
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    //# Ignore the restaurant field in JSON serialization to prevent circular references
    @JsonIgnore
    private Restaurant restaurant;

    public Long getFoodItemId() {
        return foodItemId;
    }

    public void setFoodItemId(Long v) {
        foodItemId = v;
    }

    public String getName() {
        return name;
    }

    public void setName(String v) {
        name = v;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String v) {
        description = v;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal v) {
        price = v;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String v) {
        category = v;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean v) {
        available = v;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant v) {
        restaurant = v;
    }
}
