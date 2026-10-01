package com.bank.model;

import java.util.Objects;

public class Customer {
    private final int customerId;
    private String name, email, city, phone, customerType;

    public Customer(int customerId, String name, String email, String city, String phone, String customerType) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.city = city;
        this.phone = phone;
        this.customerType = customerType;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCity() {
        return city;
    }

    public String getPhone() {
        return phone;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setName(String v) {
        name = v;
    }

    public void setEmail(String v) {
        email = v;
    }

    public void setCity(String v) {
        city = v;
    }

    public void setPhone(String v) {
        phone = v;
    }

    public void setCustomerType(String v) {
        customerType = v;
    }

    public String toString() {
        return "Customer{" + customerId + ", " + name + ", " + city + ", " + customerType + '}';
    }

    public boolean equals(Object o) {
        return this == o || o instanceof Customer c && customerId == c.customerId;
    }

    public int hashCode() {
        return Objects.hash(customerId);
    }
}