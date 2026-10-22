package com.ltm.producthive.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Author  : 10856807
 * Date    : 07-10-2026
 * Time    : 16:58
 * Project : product-restapi
 */

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prod_seq")
    @SequenceGenerator(name = "prod_seq", initialValue = 1000, allocationSize = 1)
    private Long pid;

    @Column(name="product_name", nullable = false)
    private String name;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false, length = 50)
    private String madein;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

}
