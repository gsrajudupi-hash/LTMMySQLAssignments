package com.foodexpress.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;
/**
 * Entity class representing an OrderItem.
 */
@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;
    private int quantity;
    @Column(precision = 10, scale = 2)
    private BigDecimal unitPrice;
    @Column(precision = 12, scale = 2)
    private BigDecimal subtotal;
    //# Many-to-one relationship with FoodOrder entity, fetched lazily and ignored in JSON serialization
    //# Ignore the order field in JSON serialization to prevent circular references
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private FoodOrder order;
    //# Many-to-one relationship with FoodItem entity
    //# Ignore the foodItem field in JSON serialization to prevent circular references
    @ManyToOne
    @JoinColumn(name = "food_item_id")
    @JsonIgnore
    private FoodItem foodItem;

    public Long getOrderItemId() {
        return orderItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int v) {
        quantity = v;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal v) {
        unitPrice = v;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal v) {
        subtotal = v;
    }

    public FoodOrder getOrder() {
        return order;
    }

    public void setOrder(FoodOrder v) {
        order = v;
    }

    public FoodItem getFoodItem() {
        return foodItem;
    }

    public void setFoodItem(FoodItem v) {
        foodItem = v;
    }
}
