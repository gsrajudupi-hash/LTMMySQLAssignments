package com.example.Fooddelivery.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
@Entity @Table(name="food_orders") @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class FoodOrder {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long orderId;
    @Column(nullable=false) private LocalDateTime orderDate;
    @Enumerated(EnumType.STRING) @Column(nullable=false) private OrderStatus status;
    @Column(nullable=false, precision=12, scale=2) private BigDecimal totalAmount;
    @JsonIgnore @ManyToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="customer_id", nullable=false) private Customer customer;
    @JsonIgnore @ManyToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="restaurant_id", nullable=false) private Restaurant restaurant;
    @ManyToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="address_id", nullable=false) private Address deliveryAddress;
    @OneToMany(mappedBy="order", cascade=CascadeType.ALL, orphanRemoval=true) @Builder.Default private List<OrderItem> items=new ArrayList<>();
}


