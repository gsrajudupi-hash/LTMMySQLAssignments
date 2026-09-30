package com.bank.model;

import java.util.Objects;

public class Customer {
    private final int customerId;
    private String name, email, city, phone, customerType;

    public Customer(int id, String name, String email, String city, String phone, String type) {
        this.customerId = id;
        this.name = req(name);
        this.email = req(email);
        this.city = req(city);
        this.phone = req(phone);
        this.customerType = req(type).toUpperCase();
    }

    private static String req(String s) {
        if (s == null || s.isBlank()) throw new IllegalArgumentException("Field cannot be blank");
        return s.trim();
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
        name = req(v);
    }

    public void setEmail(String v) {
        email = req(v);
    }

    public void setCity(String v) {
        city = req(v);
    }

    public void setPhone(String v) {
        phone = req(v);
    }

    public void setCustomerType(String v) {
        customerType = req(v).toUpperCase();
    }

    public String toString() {
        return "Customer{id=" + customerId + ", name='" + name + "', email='" + email + "', city='" + city + "', phone='" + phone + "', type=" + customerType + "}";
    }

    public boolean equals(Object o) {
        return this == o || (o instanceof Customer c && customerId == c.customerId);
    }

    public int hashCode() {
        return Objects.hash(customerId);
    }
}
