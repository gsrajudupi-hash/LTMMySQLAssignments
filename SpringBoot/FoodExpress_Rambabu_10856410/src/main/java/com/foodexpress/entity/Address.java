package com.foodexpress.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
/**
 * Entity class representing an Address.
 */
@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;
    private String houseNumber;
    private String street;
    private String city;
    private String pincode;
    //# Many-to-one relationship with Customer entity, fetched lazily and ignored in JSON serialization
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    //# Ignore the customer field in JSON serialization to prevent circular references
    @JsonIgnore
    private Customer customer;
// Getters and setters for the Address entity fields
    public Long getAddressId() {
        return addressId;
    }
// Setter for the addressId field
    public void setAddressId(Long v) {
        addressId = v;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    //# Getter and setter for the houseNumber field
    public void setHouseNumber(String v) {
        houseNumber = v;
    }

    //# Getter and setter for the street field
    public String getStreet() {
        return street;
    }

    public void setStreet(String v) {
        street = v;
    }

    //# Getter and setter for the city field
    public String getCity() {
        return city;
    }

    public void setCity(String v) {
        city = v;
    }

    //# Getter and setter for the pincode field
    public String getPincode() {
        return pincode;
    }

    public void setPincode(String v) {
        pincode = v;
    }

    //# Getter and setter for the customer field
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer v) {
        customer = v;
    }
}
