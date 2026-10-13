package com.foodexpress.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "restaurants")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restaurantId;

    @NotBlank(message = "Restaurant name is required")
    private String name;

    @NotBlank(message = "Location is required")
    private String location;

    @Pattern(regexp = "^[0-9]{10}$",
            message = "Contact number must be 10 digits")
    private String contactNumber;

    private Boolean active = true;

    @OneToMany(
            mappedBy = "restaurant",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<FoodItem> foodItems = new ArrayList<>();

    @OneToMany(
            mappedBy = "restaurant",
            cascade = CascadeType.ALL
    )
    private List<FoodOrder> orders = new ArrayList<>();
}