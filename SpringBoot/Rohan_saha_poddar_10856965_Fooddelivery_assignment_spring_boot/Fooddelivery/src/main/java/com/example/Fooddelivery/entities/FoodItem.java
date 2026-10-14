package com.example.Fooddelivery.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.*;
@Entity @Table(name="food_items", indexes=@Index(name="idx_food_restaurant_category", columnList="restaurant_id,category"))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class FoodItem {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long foodItemId;
    @Column(nullable=false) private String name;
    @Column(length=1000) private String description;
    @Column(nullable=false, precision=10, scale=2) private BigDecimal price;
    @Column(nullable=false) private String category;
    @Column(nullable=false) private boolean available;
    @JsonIgnore @ManyToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="restaurant_id", nullable=false) private Restaurant restaurant;
    @JsonIgnore @OneToMany(mappedBy="foodItem") @Builder.Default private List<OrderItem> orderItems=new ArrayList<>();
}
