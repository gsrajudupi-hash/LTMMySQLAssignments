package com.example.Fooddelivery.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;
@Entity @Table(name="customers", uniqueConstraints=@UniqueConstraint(columnNames="email"))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Customer {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long customerId;
    @Column(nullable=false) private String name;
    @Column(nullable=false, unique=true) private String email;
    @Column(nullable=false) private String phone;
    @JsonIgnore @Column(nullable=false) private String password;
    @JsonIgnore @OneToMany(mappedBy="customer", cascade=CascadeType.ALL, orphanRemoval=true) @Builder.Default
    private List<Address> addresses=new ArrayList<>();
    @JsonIgnore @OneToMany(mappedBy="customer") @Builder.Default private List<FoodOrder> orders=new ArrayList<>();
}


