package com.example.Fooddelivery.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;
@Entity @Table(name="restaurants") @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Restaurant {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long restaurantId;
    @Column(nullable=false) private String name;
    @Column(nullable=false) private String location;
    @Column(nullable=false) private String contactNumber;
    @Column(nullable=false) private boolean active;
    @JsonIgnore @OneToMany(mappedBy="restaurant", cascade=CascadeType.ALL, orphanRemoval=true) @Builder.Default private List<FoodItem> foodItems=new ArrayList<>();
    @JsonIgnore @OneToMany(mappedBy="restaurant") @Builder.Default private List<FoodOrder> orders=new ArrayList<>();
}
